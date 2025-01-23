import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { AllproductsComponent } from './pages/product/allproducts/allproducts.component';
import { AddproductComponent } from './pages/product/addproduct/addproduct.component';
import { SingleproductComponent } from './pages/product/singleproduct/singleproduct.component';
import { EditproductComponent } from './pages/product/editproduct/editproduct.component';
import { DeleteproductComponent } from './pages/product/deleteproduct/deleteproduct.component';
import { AllcategoriesComponent } from './pages/category/allcategories/allcategories.component';
import { AddcategoryComponent } from './pages/category/addcategory/addcategory.component';
import { EditcategoryComponent } from './pages/category/editcategory/editcategory.component';
import { DelcategoryComponent } from './pages/category/delcategory/delcategory.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forgot', component: ForgotPasswordComponent },
  { path: '', redirectTo: '/', pathMatch: 'full' },

  { path: 'product', children: [
    { path: '', component: AllproductsComponent },
    { path: 'add', component: AddproductComponent },
    { path: 'single', children: [
      { path: 'view/:id', component: SingleproductComponent },
      { path: 'edit/:id', component: EditproductComponent },
      { path: 'delete/:id', component: DeleteproductComponent },
    ]}
  ]},
  
  { path: 'category', children: [
    { path: '', component: AllcategoriesComponent },
    { path: 'products/:id', component: AllproductsComponent },
    { path: 'add', component: AddcategoryComponent },
    { path: 'single', children: [
      { path: 'edit/:id', component: EditcategoryComponent },
      { path: 'delete/:id', component: DelcategoryComponent, canActivate: [] }
    ]}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
