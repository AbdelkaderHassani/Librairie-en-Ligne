import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { catchError } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  
  private baseUrl = 'http://localhost:8080/auth/signup'; // URL de votre microservice d'authentification


  constructor(private http: HttpClient) {}


  
  // Méthode pour l'inscription
  signup(data: { email: string; password: string; lname: string; fname: string; gender: string; address: string; dob: string; }): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, data).pipe(
      catchError(error => {
        console.error('Inscription échouée', error);
        return throwError(() => new Error('Erreur lors de l’inscription'));
      })
    );
  }
  
// Méthode pour la connexion
login(data: { email: string; password: string }): Observable<any> {
  return this.http.post(`${this.baseUrl}/login`, data).pipe(
    catchError(error => {
      console.error('Connexion échouée', error);
      return throwError(() => new Error('Erreur lors de la connexion'));
    })
  );
}
}