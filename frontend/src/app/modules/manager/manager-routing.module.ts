import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AddProjectComponent } from './components/add-project/add-project.component';
import { AddTaskComponent } from './components/add-task/add-task.component';
import { EditTaskComponent } from './components/edit-task/edit-task.component';
import { EditProjectComponent } from './components/edit-project/edit-project.component';

const routes: Routes = [
  {path: "dashboard",component:DashboardComponent},
  {path: "tasks/:taskId/edit",component:EditTaskComponent},
  {path: "add-project",component:AddProjectComponent},
  {path: 'add-task/:projectId', component: AddTaskComponent },
  {path: "projects/:projectId/edit",component:EditProjectComponent},
  
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManagerRoutingModule { }
