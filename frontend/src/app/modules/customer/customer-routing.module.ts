import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AddOrderComponent } from './components/add-order/add-order.component';

const routes: Routes = [
  {path: "dashboard",component:DashboardComponent},
  {path:"add-order",component:AddOrderComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
