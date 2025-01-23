import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; 
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MyMaterialModule } from  './material.module';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { HomeComponent } from './home/home.component';

import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { AddproductComponent } from './pages/product/addproduct/addproduct.component';
import { AllproductsComponent } from './pages/product/allproducts/allproducts.component';
import { DeleteproductComponent } from './pages/product/deleteproduct/deleteproduct.component';
import { EditproductComponent } from './pages/product/editproduct/editproduct.component';
import { MyproductsComponent } from './pages/product/myproducts/myproducts.component';
import { SingleproductComponent } from './pages/product/singleproduct/singleproduct.component';

import { AddcategoryComponent } from './pages/category/addcategory/addcategory.component';
import { AllcategoriesComponent } from './pages/category/allcategories/allcategories.component';
import { DelcategoryComponent } from './pages/category/delcategory/delcategory.component';
import { EditcategoryComponent } from './pages/category/editcategory/editcategory.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './shared/footer/footer.component';
import { MatSelectModule } from '@angular/material/select';
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
   
    LoginComponent,
    RegisterComponent,
    ForgotPasswordComponent,
    AddproductComponent,
    AllproductsComponent,
    DeleteproductComponent,
    EditproductComponent,
    MyproductsComponent,
    SingleproductComponent,
    
    AddcategoryComponent,
    AllcategoriesComponent,
    DelcategoryComponent,
    EditcategoryComponent,
    NavbarComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MyMaterialModule,
    ReactiveFormsModule,
    CarouselModule,
    MatSelectModule
    
  ],
  providers: [
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
