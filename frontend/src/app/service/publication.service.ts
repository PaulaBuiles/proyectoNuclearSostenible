import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

import { PublicationDto } from '../model/publication-dto.model'; // Asegúrate de crear el modelo

@Injectable({
  providedIn: 'root'
})
export class PublicationService {

  private apiUrl = 'http://localhost:8080/api/publication'; // Ajusta la URL según sea necesario

  constructor(private http: HttpClient, private authService: AuthService) {}

  createPublication(publication: PublicationDto): Observable<any> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.http.post<any>(this.apiUrl, publication, { headers });
  }
}
