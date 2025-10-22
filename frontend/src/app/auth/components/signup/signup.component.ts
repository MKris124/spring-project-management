import { CommonModule } from '@angular/common';
import { Component, NgModule } from '@angular/core';
import { AbstractControl, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router,RouterModule } from '@angular/router';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';  // Importáljuk a szem ikonokat
import { AuthService } from '../../services/auth/auth.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule,FontAwesomeModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {
  signupForm: FormGroup;
  passwordVisible = false;  
  faEye = faEye;  
  faEyeSlash = faEyeSlash;
  constructor(private fb: FormBuilder,
     private authService: AuthService,
     private snackbar:MatSnackBar) {
    this.signupForm = this.fb.group({
      name: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required],
      confirmPassword: [null, Validators.required]
    });
  }

  onSignupSubmit() {
    const password = this.signupForm.get('password')?.value;
    const confirmPassword = this.signupForm.get('confirmPassword')?.value; 
    if (password !== confirmPassword){
      this.snackbar.open("Password missmatch error", "Close",{duration:5000});
    }
    this.authService.signup(this.signupForm.value).subscribe((res)=>{
      {
        this.snackbar.open("Signup successful","Close",{duration:5000});
      }
    })

    
  }
  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }
}
