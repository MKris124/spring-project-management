import { NgModule } from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import { LoginComponent } from "./auth/components/login/login.component";
import { SignupComponent } from "./auth/components/signup/signup.component";
import { HashLocationStrategy, LocationStrategy } from "@angular/common";
import { HomeComponent } from "./modules/public/home/home.component";

export const routes: Routes = [
    {path:"login",component:LoginComponent},
    {path:"signup",component:SignupComponent},
    {path:"admin",loadChildren:()=>import("./modules/admin/admin.module").then(e=>e.AdminModule)},
    {path:"customer",loadChildren:()=>import("./modules/customer/customer.module").then(e=>e.CustomerModule)},
    {path:"employee",loadChildren:()=>import("./modules/employee/employee.module").then(e=>e.EmployeeModule)},
    {path:"manager",loadChildren:()=>import("./modules/manager/manager.module").then(e=>e.ManagerModule)},
    {path:"public/home",component:HomeComponent}

];
@NgModule({
    imports:[RouterModule.forRoot(routes)],
    exports:[RouterModule],
    providers:[{provide:LocationStrategy,useClass: HashLocationStrategy}]
})
export class AppRoutingModule { }