import { Component } from '@angular/core';
import { ReportService } from '../../service/report.service';
import { ReportDto } from '../../model/report-dto.model';

@Component({
  selector: 'app-create-report',
  templateUrl: './create-report.component.html',
  styleUrls: ['./create-report.component.css']
})
export class CreateReportComponent {
  report: ReportDto = {
    description: '',
    denouncedId: 0,
    complainantId: 0
  };
  errorMessage: string | null = null;

  constructor(private reportService: ReportService) {}

  createReport() {
    this.reportService.createReport(this.report).subscribe({
      next: (response) => {
        console.log('Report created successfully', response);
        // Aquí puedes redirigir a otra página o mostrar un mensaje de éxito
      },
      error: (error) => {
        console.error('Error creating report', error);
        this.errorMessage = 'There was an error creating the report. Please try again.';
      }
    });
  }
}
