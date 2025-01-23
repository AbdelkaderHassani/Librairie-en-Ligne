import { Component, OnInit } from '@angular/core';
import { CategoryService } from 'src/app/services/categoryservice.service'; 
import { ProductService } from 'src/app/services/product.service'; 

@Component({
    selector: 'app-allproducts',
    templateUrl: './allproducts.component.html',
    styleUrls: ['./allproducts.component.css']
})
export class AllproductsComponent implements OnInit {

    products: any[] = []
    isLoaded: boolean = false
    errMsg: String = ""
    imgUrl = "http://localhost:8080/"

    constructor(public _product: ProductService, private _category: CategoryService) { }

    ngOnInit(): void {
        this.getMyData()
    }

    getMyData() {
        this._product.getAllProducts().subscribe(
            data => {this.products = data.data
            },
            e => {
                this.errMsg = e.message
            }
            
        )
    }

}
