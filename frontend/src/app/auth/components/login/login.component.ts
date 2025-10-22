import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormGroup, Validators, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';  // Importáljuk a szem ikonokat
import { AuthService } from '../../services/auth/auth.service';
import { StorageService } from '../../services/storage/storage.service';
import {MatSnackBar} from '@angular/material/snack-bar'; 

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule,FaIconComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm: FormGroup;
  passwordVisible = false;
  faEye = faEye;  
  faEyeSlash = faEyeSlash;
  
  constructor(private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackbar:MatSnackBar) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onLoginSubmit() {
    this.authService.login(this.loginForm.value).subscribe({
      next:(res)=>{
      if(res.userID !=null){
        const user={
          id: res.userID,
          role: res.userRole 
        }
        StorageService.saveUser(user);
        StorageService.saveToken(res.jwt);

        if(StorageService.isAdminLoggedIn())
          this.router.navigateByUrl("/admin/dashboard");
        else if(StorageService.isManagerLoggedIn())
          this.router.navigateByUrl("/manager/dashboard");
        else if(StorageService.isEmployeeLoggedIn())
          this.router.navigateByUrl("/employee/dashboard");
        else if(StorageService.isCustomerLoggedIn())
          this.router.navigateByUrl("/customer/dashboard");

        this.snackbar.open("Login successful", "Close",{duration:5000});
      } else {
        // Ha nincs userID, érvénytelen bejelentkezés
        this.snackbar.open("Invalid login. Please check your credentials.", "Close", {
          duration: 5000
        });
      }
    },
    error: (err) => {
      console.error('Login error:', err);
      this.snackbar.open("Login failed. Please check your credentials and try again.", "Close", { duration: 5000 });
    }
  })
  }
  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }
}
