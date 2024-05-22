import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthResponse } from './model/auth-response.model'; // Asegúrate de que el path sea correcto

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://tu-backend.com/api'; // URL de tu backend
  private loggedIn = new BehaviorSubject<boolean>(false); // Estado de inicio de sesión

  constructor(private http: HttpClient) { }

  // Método para verificar el estado de inicio de sesión
  isLoggedIn(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  // Método para realizar el inicio de sesión
  login(credentials: { username: string; password: string }): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/login`, credentials).pipe(
      tap(response => {
        // Si el inicio de sesión es exitoso, actualiza el estado de inicio de sesión
        if (response.token) {
          this.loggedIn.next(true);
        }
      })
    );
  }

  // Método para cerrar sesión
  logout() {
    // Aquí puedes realizar cualquier limpieza necesaria al cerrar sesión
    // Por ejemplo, eliminar el token de sesión del almacenamiento local
    // y restablecer el estado de inicio de sesión
    this.loggedIn.next(false);
  }
}
