import { Component, Inject, OnInit } from '@angular/core';
import { MatCommonModule, MatOptionModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldControl, MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { DatePipe } from '@angular/common';
@Component({
  selector: 'app-edit-task',
  standalone: true,
  imports: [
    MatCommonModule, 
    MatFormFieldModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule],
  templateUrl: './edit-task.component.html',
  styleUrl: './edit-task.component.scss',
})
export class EditTaskComponent{
  newStatus: string = "";

  constructor(
    public dialogRef: MatDialogRef<EditTaskComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.newStatus = data.currentStatus;
  }

  onSave(): void {
    if (this.newStatus) {
      this.dialogRef.close(this.newStatus); 
    } else {
      alert('Please select a status.');
    }
  }
}
