import { Component, OnInit } from '@angular/core';

import { CategoryService } from 'src/app/services/categoryservice.service'; 
@Component({
  selector: 'app-allcategories',
  templateUrl: './allcategories.component.html',
  styleUrls: ['./allcategories.component.css']
})
export class AllcategoriesComponent implements OnInit {

  categories:any[] = []
  isLoaded: boolean= false
  errMsg: String = ""
  imgUrl = "http://localhost:8080/"

  constructor(private _category:CategoryService, ) { }

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories(): void {
    this._category.getAllCategories().subscribe(
      data => {
        this.categories = data.data;  // Assurez-vous que 'data.data' est correct
        
      },
      
    );
  }
}
