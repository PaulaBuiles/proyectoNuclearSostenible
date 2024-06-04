import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { CouponDto } from '../model/coupon-dto.model';

@Injectable({
  providedIn: 'root'
})
export class CouponService {
  private baseUrl = 'http://localhost:8080/api'; // Ajusta esta URL a la correcta

  constructor(private http: HttpClient, private authService: AuthService) { }

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getAllCoupons(): Observable<CouponDto[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<CouponDto[]>(`${this.baseUrl}/coupons`, { headers });
  }

  getCouponsByUserId(userId: string): Observable<CouponDto[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<CouponDto[]>(`${this.baseUrl}/coupons/user/${userId}`, { headers });
  }

  purchaseCoupon(userId: number, couponId: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post<any>(`${this.baseUrl}/coupons/purchase`, null, { 
      headers, 
      params: {
        userId: userId.toString(),
        couponId: couponId.toString()
      } 
    });
  }
}
