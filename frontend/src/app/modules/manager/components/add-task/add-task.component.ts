import { CommonModule } from '@angular/common';
import { Component, Inject, NgModule, OnInit } from '@angular/core';
import { FormsModule, NgModel, ReactiveFormsModule } from '@angular/forms';
import { MatCommonModule, MatOptionModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { UserDto } from '../../../../models/user.dto';
import { ManagerService } from '../../services/manager.service';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { TaskDto } from '../../../../models/task.dto';
import { DatePipe } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [
    MatCommonModule, 
    MatFormFieldModule,
    MatDatepickerModule,
    ReactiveFormsModule,
    FormsModule,
    MatOptionModule,
    CommonModule,
    MatInputModule,
    MatSelectModule
  ],
  templateUrl: './add-task.component.html',
  styleUrl: './add-task.component.scss',
  providers: [DatePipe]
})
export class AddTaskComponent implements OnInit {
  users: UserDto[] = [];
  minDate: string="";
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
  

  constructor(
    public dialogRef: MatDialogRef<AddTaskComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,  
    private managerService: ManagerService,
    private snackbar: MatSnackBar,
    private datePipe: DatePipe
  ) {
    this.newTask.projectId = data.projectId;
  }

  ngOnInit(): void {
    this.loadUsers();
    const currentDate = new Date();
    this.minDate = this.datePipe.transform(currentDate, 'yyyy-MM-dd') || '';
  }

  loadUsers() {
    this.managerService.getAllEmployees().subscribe({
      next: (users) => {
        this.users = users;
      },
      error: (err) => {
        console.error('Error loading users:', err);
      }
    });
  }

  saveTask(): void {
    if (this.newTask.title && this.newTask.dueDate && this.newTask.employeeId && this.newTask.type) {
      this.managerService.createTask(this.newTask.projectId, this.newTask).subscribe({
        next: () => {
          this.snackbar.open('Task created successfully!',"Close",{duration:5000});
          this.dialogRef.close();
          location.reload();
        },
        error: (err) => {
          console.error('Error creating task:', err);
        }
      });
    } else {
      alert('Please fill in all required fields.');
    }
  }

}
