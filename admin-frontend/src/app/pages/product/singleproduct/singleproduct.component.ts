import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/authservice.service'; 
import { ProductService } from 'src/app/services/product.service'; 

@Component({
  selector: 'app-singleproduct',
  templateUrl: './singleproduct.component.html',
  styleUrls: ['./singleproduct.component.css']
})
export class SingleproductComponent implements OnInit {

  id:any
  product: any
  isLoaded: boolean = false
  errMsg: String = ""
  imgUrl: string = "http://localhost:8080/"

  constructor(private _activatedRoute:ActivatedRoute, private _data:ProductService, public _auth:AuthService) { }

  ngOnInit(): void {
    this.id = this._activatedRoute.snapshot.params["id"] //req.params.id
    this.getSingle(this.id)
  }

  getSingle(id:any){
    this._data.getSingleProduct(id).subscribe(
      result => {
        // console.log(result.data)
        this.product = result.data
      },
      e => {
        this.errMsg = e.message
        
      },
      
    )
  }

}
