import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserDto } from '../model/user-dto.model'; // Ajusta la ruta según tu estructura de proyecto
import { AuthService } from './auth.service'; // Ajusta la ruta según tu estructura de proyecto

@Injectable({
  providedIn: 'root'
})
export class RequestsService {
  private baseUrl = 'http://localhost:8080/api'; // URL de tu backend

  constructor(private http: HttpClient, private authService: AuthService) {}

  // Método para obtener un usuario por ID
  getUserById(id: number): Observable<UserDto> {
    // Obtener el token del servicio de autenticación
    const token = this.authService.getToken();
    console.log(token);

    // Crear un objeto HttpHeaders con el token en el encabezado
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    // Pasar las cabeceras personalizadas en la solicitud HTTP
    return this.http.get<UserDto>(`${this.baseUrl}/user/${id}`, { headers });
  }
}
