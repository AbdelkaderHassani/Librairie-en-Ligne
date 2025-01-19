import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  getAllBooks(): Observable<any> {
    return this.http.get(`${this.baseUrl}${environment.bookApi.books}`);
  }

  getBookById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}${environment.bookApi.books}/${id}`);
  }

  getCategories(): Observable<any> {
    return this.http.get(`${this.baseUrl}${environment.bookApi.categories}`);
  }

  getBooksByCategory(categoryId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}${environment.bookApi.books}/category/${categoryId}`);
  }
}
