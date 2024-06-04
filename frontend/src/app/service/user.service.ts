import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserDto } from '../model/user-dto.model';
import { AuthService } from './auth.service';
import { CouponDto } from '../model/coupon-dto.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getUserById(id: string): Observable<UserDto> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<UserDto>(`${this.baseUrl}/user/${id}`, { headers });
  }

  getRatingByUserId(id: string): Observable<Number> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<Number>(`${this.baseUrl}/assessments/average-rating/user/${id}`, { headers });
  }
  
}
