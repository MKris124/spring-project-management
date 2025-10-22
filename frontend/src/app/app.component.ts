import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { StorageService } from './auth/services/storage/storage.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
    title="Project Management";
    isAdminLoggedIn: boolean=StorageService.isAdminLoggedIn();
    isManagerLoggedIn: boolean=StorageService.isManagerLoggedIn();
    isEmployeeLoggedIn: boolean=StorageService.isEmployeeLoggedIn();
    isCustomerLoggedIn: boolean=StorageService.isCustomerLoggedIn();

    constructor(private router:Router){}

    ngOnInit(){
      this.router.events.subscribe(events=>{
        this.isAdminLoggedIn=StorageService.isAdminLoggedIn();
        this.isManagerLoggedIn=StorageService.isManagerLoggedIn();
        this.isEmployeeLoggedIn=StorageService.isEmployeeLoggedIn();
        this.isCustomerLoggedIn=StorageService.isCustomerLoggedIn();
      })
    }
    logout(){
      StorageService.logout();
      this.router.navigateByUrl("/login");
    }
    
    
}
