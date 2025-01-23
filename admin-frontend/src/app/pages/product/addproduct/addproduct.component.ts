import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Product } from 'src/app/models/product';
import { CategoryService } from 'src/app/services/categoryservice.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-addproduct',
  templateUrl: './addproduct.component.html',
  styleUrls: ['./addproduct.component.css']
})
export class AddproductComponent implements OnInit {

  errMsg: any = {};
  categories: any[] = [];
  file: File | null = null;

  productForm: FormGroup = new FormGroup({
    title: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    price: new FormControl('', [Validators.required, Validators.min(0)]),
    categoryId: new FormControl('', [Validators.required]),
    productImg: new FormControl(null),
    usbn: new FormControl('', [Validators.required]),
    auteur: new FormControl('', [Validators.required]),
    datearrivage: new FormControl('', [Validators.required]),
    quantity: new FormControl('', [Validators.required, Validators.min(1)])
  });

  constructor(
    private _data: ProductService,
    private _router: Router,
    private _category: CategoryService
  ) {}

  ngOnInit(): void {
    this.getCategories();
  }
  
  handleProduct() {
    if (this.productForm.invalid) {
      this.productForm.markAllAsTouched();
      console.log("Form is invalid:", this.productForm.errors);
      return;
    }

    const formData = this.productForm.value;
    console.log("Form submitted with data:", formData);

    const productData: Product = {
      id: '',
      userId: '',
      categoryId: formData.categoryId ?? '',
      title: formData.title ?? '',
      description: formData.description ?? '',
      price: formData.price ?? 0,
      usbn: formData.usbn ?? '',
      auteur: formData.auteur ?? '',
      datearrivage: formData.datearrivage ?? '',
      productImg: this.file?.name ?? 'defaultProduct.png',
      quantity: formData.quantity ?? 1
    };

    this._data.addProduct(productData).subscribe(
      res => {
        console.log("Product added successfully:", res);
        this._router.navigateByUrl("product");
      },
      error => {
        console.error("Error adding product:", error);
        if (error.error?.message) {
          this.errMsg = error.error.message;
        }
      }
    );
  }

  getCategories() {
    this._category.getAllCategories().subscribe(
      res => {
        if (res?.data) {
          this.categories = res.data;
        } else {
          this.categories = [];
        }
      },
      error => {
        console.error("Error fetching categories:", error);
        this.categories = [];
      }
    );
  }

  onChangeImg(event: any) {
    if (event.target?.files?.length > 0) {
      this.file = event.target.files[0];
      this.productForm.patchValue({
        productImg: this._category.imgUpload
      });
    }
  }

  get title() { return this.productForm.get('title')!; }
  get description() { return this.productForm.get('description')!; }
  get price() { return this.productForm.get('price')!; }
  get categoryId() { return this.productForm.get('categoryId')!; }
  get usbn() { return this.productForm.get('usbn')!; }
  get auteur() { return this.productForm.get('auteur')!; }
  get datearrivage() { return this.productForm.get('datearrivage')!; }
  get quantity() { return this.productForm.get('quantity')!; }

}
