import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductDto } from '../model/product-dto.model';
import { AuthService } from './auth.service';


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
}
