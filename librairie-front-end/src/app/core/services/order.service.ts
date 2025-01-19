import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  createOrder(orderData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}${environment.orderApi.orders}`, orderData);
  }

  getOrders(): Observable<any> {
    return this.http.get(`${this.baseUrl}${environment.orderApi.orders}`);
  }

  getOrderById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}${environment.orderApi.orders}/${id}`);
  }

  addToCart(bookId: number, quantity: number): Observable<any> {
    return this.http.post(`${this.baseUrl}${environment.orderApi.cart}`, { bookId, quantity });
  }

  getCart(): Observable<any> {
    return this.http.get(`${this.baseUrl}${environment.orderApi.cart}`);
  }

  updateCartItem(itemId: number, quantity: number): Observable<any> {
    return this.http.put(`${this.baseUrl}${environment.orderApi.cart}/${itemId}`, { quantity });
  }

  removeFromCart(itemId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}${environment.orderApi.cart}/${itemId}`);
  }
}
