import { Routes } from '@angular/router';
import { SignupComponent } from './auth/components/signup/signup.component';
import { LoginComponent } from './auth/components/login/login.component';
import { PagenotfoundComponent } from './modules/shared/pagenotfound/pagenotfound.component';
import { AdminDashboardComponent } from './modules/admin/components/admin-dashboard/admin-dashboard.component';
import { CustomerDashboardComponent } from './modules/customer/components/customer-dashboard/customer-dashboard.component';
import { HomeComponent } from './modules/shared/home/home.component';

export const routes: Routes = [
    {path:"signup",component: SignupComponent},
    {path:"login",component: LoginComponent},
    {path:"admin/dashboard",component: AdminDashboardComponent},
    {path:"customer/dashboard",component: CustomerDashboardComponent},
    {path:"login",component: LoginComponent},
    {path:"",component: HomeComponent},
    {path:"**",component: PagenotfoundComponent},
    
];
