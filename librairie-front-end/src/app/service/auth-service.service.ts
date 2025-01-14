import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth'; // URL de votre microservice d'authentification
 


  constructor(private http: HttpClient) {}


  // Méthode pour l'inscription
  signup(data: { email: string; password: string; lname: string; fname: string;  }): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, data);
  }


  // Méthode pour la connexion
  login(data: { email: string; password: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, data);
  }
}
