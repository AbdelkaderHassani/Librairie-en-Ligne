import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from 'src/app/models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  baseUrl = "http://localhost:8080/product/";

  constructor(private _http: HttpClient) { }

  getAllCategories(): Observable<any> {
    return this._http.get(`${this.baseUrl}`);
  }

  getAllProducts(): Observable<any> {
    return this._http.get(`${this.baseUrl}`);
  }

  getCategoryById(categoryId: string): Observable<any> {
    return this._http.get(`http://localhost:8080/category/${categoryId}`);
  }

  getSingleProduct(id: string): Observable<any> {
    return this._http.get(`${this.baseUrl}single/${id}`);
  }

  addProduct(data: Product): Observable<any> {
    return this._http.post(`${this.baseUrl}add`, data);
  }

  delProduct(id: string): Observable<any> {
    return this._http.delete(`${this.baseUrl}single/${id}`);
  }

  editProduct(id: string, data: Product): Observable<any> {
    return this._http.patch(`${this.baseUrl}single/${id}`, data);
  }

  myProducts(): Observable<any> {
    return this._http.get(`${this.baseUrl}myProducts`);
  }

  imgUpload(id: string, data: any): Observable<any> {
    return this._http.post(`${this.baseUrl}productImg/${id}`, data);
  }

  // Nouvelle méthode pour ajouter une quantité à un produit existant
  addQuantityToProduct(productId: string, quantity: number): Observable<any> {
    return this._http.patch(`${this.baseUrl}addQuantity/${productId}`, { quantity });
  }

  // Méthode pour mettre à jour la quantité d'un produit
  updateProductQuantity(productId: string, newQuantity: number): Observable<any> {
    return this._http.patch(`${this.baseUrl}updateQuantity/${productId}`, { newQuantity });
  }

}
