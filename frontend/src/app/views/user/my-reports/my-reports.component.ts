import { Component, OnInit } from '@angular/core';
import { ReportService } from '../../../service/report.service';
import { ReportDto } from '../../../model/report-dto.model';
import { ReportEntity } from '../../../model/report-entity.model';

@Component({
  selector: 'app-my-reports',
  templateUrl: './my-reports.component.html',
  styleUrls: ['./my-reports.component.css']
})
export class MyReportsComponent implements OnInit {
  reportsMade: ReportEntity[] = [];
  reportsReceived: ReportEntity[] = [];// Supón que obtienes esto del servicio de autenticación

  constructor(private reportService: ReportService) {}

  ngOnInit(): void {
    this.getReportsByUserId(Number(localStorage.getItem('userId')));
  }

  getReportsByUserId(userId: number): void {
    this.reportService.getReportsByUserId(userId).subscribe(
      (reports: ReportEntity[]) => {
        console.log('Reports:', reports);
        reports.forEach(report => {
          if (report.complainant.idUser === userId) {
            this.reportsMade.push(report);
          } else if (report.denounced.idUser === userId) {
            this.reportsReceived.push(report);
          }
        });
        console.log('Reports Made:', this.reportsMade);
        console.log('Reports Received:', this.reportsReceived);
      },
      (error: any) => {
        console.error('Error fetching reports', error);
      }
    );
  }
}
