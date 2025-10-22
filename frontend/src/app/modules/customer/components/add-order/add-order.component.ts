import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatDialogRef } from '@angular/material/dialog';
import { Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-add-order',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule, 
    ReactiveFormsModule],
  templateUrl: './add-order.component.html',
  styleUrl: './add-order.component.scss'
})
export class AddOrderComponent implements OnInit {
  orderForm!: FormGroup;

  constructor(private fb: FormBuilder,
    public dialogRef: MatDialogRef<AddOrderComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}
  ngOnInit() {
    this.orderForm = this.fb.group({
      orderDetails: ['', Validators.required]
    });
  }

  placeOrder(): void {
      if (this.orderForm.valid) {
        this.dialogRef.close(this.orderForm.value); 
      }
    }
}
