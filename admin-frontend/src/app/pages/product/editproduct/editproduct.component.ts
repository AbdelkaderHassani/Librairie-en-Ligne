import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/authservice.service'; 
import { CategoryService } from 'src/app/services/categoryservice.service'; 
import { ProductService } from 'src/app/services/product.service'; 
@Component({
  selector: 'app-editproduct',
  templateUrl: './editproduct.component.html',
  styleUrls: ['./editproduct.component.css']
})
export class EditproductComponent implements OnInit {

  errMsg: any = {};
  product: any = {};
  selectedCategory: any = {};
  categories: any[] = [];
  id: any;
  file: any;
  myData: FormData = new FormData();

  editForm: FormGroup = new FormGroup({
    title: new FormControl("", [Validators.required]),
    description: new FormControl("", [Validators.required]),
    price: new FormControl("", [Validators.required]),
    categoryId: new FormControl("", [Validators.required]),
    gtin: new FormControl("", [Validators.required]),
    marque: new FormControl("", [Validators.required]),
    processor: new FormControl("", [Validators.required]),
    cacheMemory: new FormControl("", [Validators.required]),
    stockage: new FormControl("", [Validators.required]),
    quantity: new FormControl("", [Validators.required]) // Ajout du champ quantity
  });

  constructor(
    private _data: ProductService,
    private _router: Router,
    private _activatedRoute: ActivatedRoute,
    private _auth: AuthService,
    private _category: CategoryService
  ) { }

  ngOnInit(): void {
    this.id = this._activatedRoute.snapshot.params["id"];
    this.getSingle();
    this.getCategories();
  }

  handleEdit() {
    if (this.product) {
      this._data.editProduct(this.product["_id"], this.editForm.value)
        .subscribe(
          (res) => {
            if (this.file) this.submitImage();
          },
          (e) => {
            // Gestion des erreurs
            console.log(e.error);
            if (e.error.message.includes("title")) this.errMsg.title = e.error.data.errors.title.message;
            if (e.error.message.includes("price")) this.errMsg.price = e.error.data.errors.price.message;
            if (e.error.message.includes("categoryId")) this.errMsg.categoryId = e.error.data.errors.categoryId.message;
            if (e.error.message.includes("usbn")) this.errMsg.usbn = e.error.data.errors.usbn.message;
            if (e.error.message.includes("auteur")) this.errMsg.auteur = e.error.data.errors.auteur.message;
            if (e.error.message.includes("description")) this.errMsg.description = e.error.data.errors.description.message;
            if (e.error.message.includes("datearrivage")) this.errMsg.datearrivage = e.error.data.errors.datearrivage.message;
            
          },
          () => {
            this._router.navigateByUrl(`product/single/view/${this.id}`);
          }
        );
    }
  }

  getSingle() {
    this._data.getSingleProduct(this.id).subscribe(
      result => {
        this.product = result.data;
        this.selectedCategory = this.product.categoryId;
        this.editForm.patchValue(result.data);
      },
      e => {
        this.errMsg = e.message;
      }
    );
  }

  getCategories() {
    this._category.getAllCategories().subscribe(
      res => {
        this.categories = res.data;
      },
      e => {
        console.log(e);
      }
    );
  }

  onChangeImg(event: any) {
    this.file = event.target.files[0];
  }

  submitImage() {
    this.myData.append("productImg", this.file, this.file.name)
    this._data.imgUpload(this.id, this.myData).subscribe(
      res => console.log(res),
      e => console.log(e)
    )
  }
  get title() { return this.editForm.get("title"); }
  get description() { return this.editForm.get("description"); }
  get price() { return this.editForm.get("price"); }
  get categoryId() { return this.editForm.get("categoryId"); }
  get usbn() { return this.editForm.get("usbn"); }
  get auteur() { return this.editForm.get("auteur"); }
  get datearrivage() { return this.editForm.get("datearrivage"); }
  get quantity() { return this.editForm.get("quantity"); } // Fonction pour récupérer le contrôle de la quantité
}



