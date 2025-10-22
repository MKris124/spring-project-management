import { Component, OnInit } from '@angular/core';
import { ManagerService } from '../../services/manager.service';
import { ProjectDto } from '../../../../models/project.dto';
import { StorageService } from '../../../../auth/services/storage/storage.service';
import { CommonModule } from '@angular/common';
import { TaskDto } from '../../../../models/task.dto';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AddTaskComponent } from '../add-task/add-task.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EditTaskComponent } from '../edit-task/edit-task.component';
import { EditProjectComponent } from '../edit-project/edit-project.component';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule,
    MatDialogModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  projects: ProjectDto[] = [];
  order:any;
  names: string[] = [];
  seenEmployees = new Set<string>();
  orderedProjects: any; 
  offers:any; // Megrendelt projektek
  selectedProject: ProjectDto | null = null;
  newTask: TaskDto = {
    id: 0,
    title: '',
    type: 'FELADAT',
    status: 'FOLYAMATBAN',
    dueDate: new Date(),
    employeeId: 0,
    employeeName: '',
    projectId: 0,
  };
  projectPhases: string[] = ['ÉRDEKLŐDÉS', 'MINŐSíTÉS','AJÁNLATADÁS','MEGRENDELÉS', 'KIVITELEZÉS','LEZÁRT','ELVESZTETT'];

  constructor(
    private managerService: ManagerService,
    public dialog: MatDialog,
    private snackbar: MatSnackBar) { 
    }

  ngOnInit(): void {
    this.loadProjects();
    this.loadOrderedProjects();  
    this.loadOffers();
  }
  

  loadProjects() {
    const managerId = StorageService.getUserId();
    this.managerService.getProjects(managerId).subscribe((data) => {
      this.projects = data;

      this.projects.forEach((project) => {
        this.managerService.getTaskById(project.id).subscribe((tasks: TaskDto[]) => {
          project.tasks = tasks;
        }); 
      });
      
    });
    

  }
  
  

  selectProject(project: ProjectDto) {
    this.selectedProject = project;
  }

  deleteProject(projectId: number) {
    const confirmDelete = window.confirm('Are you sure want to delete the project?');

  if (confirmDelete) {
    this.managerService.removeProject(projectId).subscribe({
      next: () => {
        this.snackbar.open('Project deleted', "Close", {duration:5000});
        this.loadProjects(); 
        localStorage.removeItem(`offerPlaced_${projectId}`);
        localStorage.removeItem(`orderPlaced_${projectId}`);
        this.loadOffers();
        this.loadOrderedProjects();
      },
      error: (err) => {
        console.error('Error deleting project phase:', err);
      }
    });
  }
  }


  addTask(projectId: number) {
    const dialogRef = this.dialog.open(AddTaskComponent, {
      width: '400px',
      data: {
        title: '',
        dueDate: new Date(),
        employeeId: null,
        type: 'FELADAT',
        projectId: projectId
      }
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.managerService.createTask(projectId, result).subscribe({
          next: () => {
            this.snackbar.open('Task created successfully!',"Close",{duration:5000});
            this.loadProjects();  
            
          },
          error: (err) => {
            console.error('Error creating task:', err);
          }
        });
      }
    });
  }

  navigateToProject(projectId: number): void {
    const project = this.projects.find(p => p.id === projectId);
    if (project) {
      this.updateProjectPhase(project);
    }
  }
  
  updateProjectPhase(project: ProjectDto): void {
    const dialogRef = this.dialog.open(EditProjectComponent, {
      width: '300px',
      data: { currentPhase: project.phase }
    });

    dialogRef.afterClosed().subscribe(newPhase => {
      if (newPhase && newPhase !== project.phase) {
        this.managerService.updateProjectPhase(project.id, newPhase).subscribe({
          next: () => {
            this.snackbar.open('Project phase updated successfully!',"Close", {duration:5000});
            this.loadProjects(); 
          },
          error: (err) => {
            console.error('Error updating task status:', err);
          }
        });
      }
    });
  }

  
  navigateToTask(taskId: number): void {
    const task = this.projects.flatMap(p => p.tasks).find(t => t.id === taskId);
    if (task) {
      this.openEditTaskStatusDialog(task);
    }
  }

  openEditTaskStatusDialog(task: TaskDto): void {
    const dialogRef = this.dialog.open(EditTaskComponent, {
      width: '300px',
      data: { currentStatus: task.status }
    });

    dialogRef.afterClosed().subscribe(newStatus => {
      if (newStatus && newStatus !== task.status) {
        this.managerService.updateTaskStatus(task.id, newStatus).subscribe({
          next: () => {
            this.snackbar.open('Task status updated successfully!',"Close", {duration:5000});
            this.loadProjects(); 
          },
          error: (err) => {
            console.error('Error updating task status:', err);
          }
        });
      }
    });
  }
  loadOffers(){
    this.managerService.getPendingOffers().subscribe((data=> {
      this.offers=data;
    }))
  }

  loadOrderedProjects() {
    this.managerService.getPendingOrders().subscribe((data) => {
      this.orderedProjects = data;
    });
  }
  // Megrendelés elfogadása
  acceptOrder(orderId: number, projectId:number): void {
    const managerId = Number(StorageService.getUserId()); // Az aktuális projektmenedzser ID-jának lekérése
  
    this.managerService.acceptOrder(orderId, managerId).subscribe({
      next: (response) => {
        this.snackbar.open('Order accepted successfully!', 'Close', { duration: 5000 });
        localStorage.removeItem(`orderPlaced_${projectId}`);
        this.loadProjects();
        this.loadOrderedProjects(); 
      },
      error: (err) => {
        console.error('Error accepting order:', err);
        this.snackbar.open('Failed to accept order. Try again later.', 'Close', { duration: 5000 });
      }
    });
  }
  

  // Megrendelés elutasítása
  rejectOrder(orderId: number, projectId:number): void {
    this.managerService.rejectOrder(orderId).subscribe({
      next: () => {
        this.loadOrderedProjects(); 
         // Frissíti a megrendelt projektek listáját
        this.snackbar.open('Order rejected successfully!',"Close",{duration:5000});
        localStorage.removeItem(`orderPlaced_${projectId}`);
      },
      error: (err) => {
        console.error('Error rejecting order:', err);
      }
    });
  }
  acceptOffer(offerId: number, projectId:number): void {
    const managerId = Number(StorageService.getUserId()); // Az aktuális projektmenedzser ID-jának lekérése
  
    this.managerService.acceptOffer(offerId, managerId).subscribe({
      next: (response) => {
        this.snackbar.open('Order accepted successfully!', 'Close', { duration: 5000 });
        this.loadOffers(); 
        this.loadProjects();
        localStorage.removeItem(`offerPlaced_${projectId}`);
      },
      error: (err) => {
        console.error('Error accepting order:', err);
        this.snackbar.open('Failed to accept order. Try again later.', 'Close', { duration: 5000 });
      }
    });
    
  }

  rejectOffer(offerId: number, projectId:number): void {
    this.managerService.rejectOffer(offerId).subscribe({
      next: () => {
        this.loadOffers();  // Frissíti a megrendelt projektek listáját
        alert('Order rejected successfully!');
        localStorage.removeItem(`offerPlaced_${projectId}`);
      },
      error: (err) => {
        console.error('Error rejecting order:', err);
      }
    });
   
    
  }
}
