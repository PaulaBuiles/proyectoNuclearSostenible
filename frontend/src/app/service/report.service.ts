import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReportDto } from './../model/report-dto.model';
import { ReportEntity } from './../model/report-entity.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private apiUrl = 'http://localhost:8080/api/reports';

  constructor(private http: HttpClient, private authService: AuthService) {}

  createReport(report: ReportDto): Observable<any> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.post<any>(this.apiUrl, report, { headers });
  }
  
  getReportsByUserId(userId: number): Observable<ReportEntity[]> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const url = `${this.apiUrl}/list-user/${userId}`;
    return this.http.get<any>(url, { headers });
  }

}
