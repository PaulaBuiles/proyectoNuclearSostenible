import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthResponse } from '../model/auth-response.model'; // Asegúrate de que el path sea correcto

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8080/api'; // URL de tu backend
  private loggedIn = new BehaviorSubject<boolean>(false); // Estado de inicio de sesión

  constructor(private http: HttpClient) {}

  // Método para verificar el estado de inicio de sesión
  isLoggedIn(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  // Método para obtener el token
  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  // Método para realizar el inicio de sesión
  login(userDto: { username: string; password: string }): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/persona/autenticar`, userDto).pipe(
      tap(response => {
        const token = response.authenticationResponseDto?.token;
        if (token) {
          localStorage.setItem('authToken', token);
          localStorage.setItem('userId', response.user?.idUser.toString());
          this.loggedIn.next(true);
        }
      })
    );
  }

  // Método para cerrar sesión
  logout() {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userId');
    this.loggedIn.next(false);
  }
}
