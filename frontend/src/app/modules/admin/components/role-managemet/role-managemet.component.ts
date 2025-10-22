import { Component, OnInit } from '@angular/core';
import { UserDto } from '../../../../models/user.dto';
import { AdminService } from '../../services/admin.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-role-managemet',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './role-managemet.component.html',
  styleUrls: ['./role-managemet.component.scss']  
})
export class RoleManagemetComponent implements OnInit {
  employees: UserDto[] = [];
  customers: UserDto[] = [];
  roles: string[] = ['PROJECT_MANAGER', 'USER'];
  newPasswords: { [key: number]: string } = {};  

  constructor(private adminService: AdminService,private snackbar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadEmployees();
    this.loadCustomers();
  }

  loadEmployees() {
    this.adminService.getEmployees().subscribe(data => {
      this.employees = data;
    });
  }

  loadCustomers() {
    this.adminService.getCustomers().subscribe(data => {
      this.customers = data;
    });
  }

  deleteUser(userId: number | null) {
    if (userId !== null) {
      this.adminService.deleteUser(userId).subscribe(() => {
        this.snackbar.open('User deleted successfully!','Close',{duration: 5000});
        this.loadEmployees();
        this.loadCustomers();
        
      });
    } else {
      alert('No user selected for deletion.');
    }
  }

  updateRole(userId:number|null, newRole:string) {  
    if (userId !== null) {
      this.adminService.updateUserRole(userId,newRole).subscribe(() => {
        this.snackbar.open('User role updated successfully!','Close',{duration: 5000});
      });
    } else {
      alert('No user selected for role update.');
    }
  }

  resetPassword(userId: number, newPassword: string) {
    if (!newPassword) {
      alert('Please enter a new password');
      return;
    }
    this.adminService.resetPassword(userId, newPassword).subscribe(() => {
      
      this.newPasswords[userId] = ''; 
    });
    this.snackbar.open('Password reset successfully!','Close', {duration: 5000});
  }

}
