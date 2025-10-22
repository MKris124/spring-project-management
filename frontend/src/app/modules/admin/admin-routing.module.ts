import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleManagemetComponent } from './components/role-managemet/role-managemet.component';

const routes: Routes = [
  {path: "dashboard",component:RoleManagemetComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
