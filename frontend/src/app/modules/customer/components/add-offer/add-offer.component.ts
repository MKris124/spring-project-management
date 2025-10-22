import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { OfferDto } from '../../../../models/offer.dto';
import { CustomerService } from '../../services/customer.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { StorageService } from '../../../../auth/services/storage/storage.service';
import { MatDialogRef } from '@angular/material/dialog';
import { Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-add-offer',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule  
  ],
  templateUrl: './add-offer.component.html',
  styleUrl: './add-offer.component.scss'
})
export class AddOfferComponent implements OnInit {
  offerForm!: FormGroup;
  constructor(private fb: FormBuilder,
    public dialogRef: MatDialogRef<AddOfferComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    this.offerForm = this.fb.group({
      projectId: [this.data.projectId, Validators.required],
      offerDetails: ['', Validators.required],
      status: ['PENDING', Validators.required] 
    });
  }

  onSubmit(): void {
    if (this.offerForm.valid) {
      this.dialogRef.close(this.offerForm.value); 
    }
  }
}
