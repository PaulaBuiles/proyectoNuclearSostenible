import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserDto } from '../model/user-dto.model';
import { ProductDto } from '../model/product-dto.model';
import { PublicationDto } from '../model/publication-dto.model';
import { AuthService } from './auth.service';
import { Token } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class PublicationInfoService {

  private baseUrl = 'http://localhost:8080/api'; // Ajusta esta URL a la correcta

  constructor(private http: HttpClient, private authService: AuthService) { }

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getPublicationByProductId(productId: number): Observable<PublicationDto> {
    const headers = this.getAuthHeaders();
    return this.http.get<PublicationDto>(`${this.baseUrl}/product/${productId}/last-publication`, { headers });
  }

  getProductById(productId: number): Observable<ProductDto> {
    const headers = this.getAuthHeaders();
    return this.http.get<ProductDto>(`${this.baseUrl}/product/${productId}`, { headers });
  }

  getUserById(userId: number): Observable<UserDto> {
    const headers = this.getAuthHeaders();
    return this.http.get<UserDto>(`${this.baseUrl}/user/${userId}`, { headers });
  }
}
