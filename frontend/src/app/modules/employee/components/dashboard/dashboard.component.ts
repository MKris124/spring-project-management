import { Component} from '@angular/core';
import { OnInit } from '@angular/core';
import { TaskDto } from '../../../../models/task.dto';
import { EmployeeService } from '../../services/employee.service';
import { StorageService } from '../../../../auth/services/storage/storage.service';
import { FormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    FormsModule, 
    CommonModule,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  tasks: TaskDto[] = [];
  statusOptions: string[] = ['FOLYAMATBAN', 'TELJESÍTVE', 'ELHALASZTVA'];

  constructor(
    private employeeService: EmployeeService,
    private snackbar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks() {
    const userId = StorageService.getUserId();
    this.employeeService.getAssignedTasks(Number(userId)).subscribe({
      next: (tasks) => {
        this.tasks = tasks;
      },
      error: (err) => {
        console.error('Error loading tasks:', err);
      }
    });
  }

  updateTaskStatus(task: TaskDto, newStatus: string) {
    this.employeeService.updateTaskStatus(task.id, newStatus).subscribe({
      next: () => {
        task.status = newStatus;
        this.snackbar.open('Task status updated successfully!','Close',{duration:5000});
      },
      error: (err) => {
        console.error('Error updating task status:', err);
      }
    });
  }
}
