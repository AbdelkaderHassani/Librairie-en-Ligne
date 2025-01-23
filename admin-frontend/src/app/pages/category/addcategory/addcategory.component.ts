import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/authservice.service'; 
import { CategoryService } from 'src/app/services/categoryservice.service'; 

@Component({
  selector: 'app-addcategory',
  templateUrl: './addcategory.component.html',
  styleUrls: ['./addcategory.component.css']
})
export class AddcategoryComponent implements OnInit {

  errMsg: any = {};
  id: any;
  file: any;
  myData: FormData = new FormData();

  categoryForm: FormGroup = new FormGroup({
    name: new FormControl("", [Validators.required]),
    desc: new FormControl("", [Validators.required]),
    categoryImg: new FormControl(null)
  });

  constructor(private _category: CategoryService, private _router: Router, public _auth: AuthService) { }

  get name() {
    return this.categoryForm.get("name");
  }
  
  get desc() {
    return this.categoryForm.get("desc");
  }

  get categoryImg() {
    return this.categoryForm.get("categoryImg");
  }

  ngOnInit(): void { }

  handleCategory() {
    this.myData.set("name", this.name?.value);
    this.myData.set("desc", this.desc?.value);
    if (this.file) this.myData.set("categoryImg", this.categoryImg?.value);
    this._category.addCategory(this.myData).subscribe(
      res => {
        console.log(res);
      },
      e => {
        console.log(e.error);
        if (e.error?.message) {
          if (e.error.message.includes("name")) this.errMsg.name = e.error.data.errors.name.message;
          if (e.error.message.includes("desc")) this.errMsg.desc = e.error.data.errors.desc.message;
          if (e.error.message.includes("categoryImg")) this.errMsg.categoryImg = e.error.data.errors.categoryImg.message;
        }
      },
      () => {
        this._router.navigateByUrl("category");
      }
    );
  }

  onChangeImg(event: any) {
    this.file = event.target.files[0];
    this.categoryForm.patchValue({
      categoryImg: this.file,
    });
    console.log((this.categoryImg));
  }

  submitImage() {
    this.myData.append("name", this.name?.value);
    this.myData.append("desc", this.desc?.value);
    this.myData.append("categoryImg", this.categoryImg?.value);
    console.log(this.myData.get("name"));
    console.log(this.myData.get("desc"));
    console.log(this.myData.get("categoryImg"));
    return this.myData;
  }
}
