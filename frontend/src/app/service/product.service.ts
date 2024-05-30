import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductDto } from '../model/product-dto.model';
import { AuthService } from './auth.service';
import { PublicationDto } from '../model/publication-dto.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiUrl = 'http://localhost:8080/api/product'; // Ajusta la URL según sea necesario

  constructor(private http: HttpClient, private authService: AuthService) {}

  createProduct(product: ProductDto): Observable<any> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.post<any>(this.apiUrl, product, { headers });
  }

  getAllProducts(): Observable<ProductDto[]> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<ProductDto[]>(this.apiUrl, { headers });
  }
  getProductsByUserId(userId: number): Observable<ProductDto[]> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const url = `${this.apiUrl}/list-user/${userId}`;
    return this.http.get<ProductDto[]>(url, { headers });
  }
  getProductById(id: number): Observable<ProductDto> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<ProductDto>(url, { headers });
  }

  getPublicationByProductId(productId: number): Observable<PublicationDto> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<PublicationDto>(`${this.apiUrl}/${productId}/last-publication`, { headers });
  }
}
