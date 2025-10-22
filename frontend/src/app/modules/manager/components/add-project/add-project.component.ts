import { Component } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder } from '@angular/forms';
import { OnInit } from '@angular/core';
import { ManagerService } from '../../services/manager.service';
import { ProjectDto } from '../../../../models/project.dto';
import { Validators } from '@angular/forms';
import { StorageService } from '../../../../auth/services/storage/storage.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-project',
  standalone: true,
  imports: [
    MatFormFieldModule,
    CommonModule,
    ReactiveFormsModule,
    MatSelectModule
  ],
  templateUrl: './add-project.component.html',
  styleUrl: './add-project.component.scss'
})
export class AddProjectComponent implements OnInit {
  projectForm!: FormGroup;
  projectPhases: string[] = ['ÉRDEKLŐDÉS', 'MINŐSÍTÉS', 'AJÁNLATADÁS', 'MEGRENDELÉS', 'KIVITELEZÉS'];

  constructor(
    private fb: FormBuilder, 
    private managerService: ManagerService, 
    private snackbar: MatSnackBar,
    private router: Router) {}

  ngOnInit(): void {
    this.projectForm = this.fb.group({
      name: ['', Validators.required],
      phase: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.projectForm.valid) {
      const newProject: ProjectDto = {
        id: 0,  
        name: this.projectForm.value.name,
        phase: this.projectForm.value.phase,
        managerId: Number(StorageService.getUserId()),  
        tasks: []  
      };

      this.managerService.createProject(Number(StorageService.getUserId()),newProject).subscribe({
        next: () => {
          this.snackbar.open('Project added successfully!',"Close",{duration: 5000});
          this.projectForm.reset();
          this.router.navigate(["/manager/dashboard"]);
        },
        error: (err) => {
          console.error('Error adding project:', err);
        }
      });
      
    }
  }
}