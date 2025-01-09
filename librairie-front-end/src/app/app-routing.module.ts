import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';

const routes: Routes = [
  
 {path:'' , component:HomeComponent},
 {path:'login' , component:LoginComponent},
 {path:'register' , component:RegisterComponent},
 {path:'forgot' , component:ForgotPasswordComponent},
 { path: '', redirectTo: '/', pathMatch: 'full' },
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
