import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
    denouncedId: 0, // Se establecerá con el valor de la URL
    complainantId: 0 // Se establecerá con el valor del almacenamiento local
  };
  errorMessage: string | null = null;

  constructor(private route: ActivatedRoute, private reportService: ReportService) {
    // Obtener el ID del usuario denunciado de la URL
    this.route.paramMap.subscribe(params => {
      const denouncedId = localStorage.getItem('denoucedId');;
      if (denouncedId) {
        this.report.denouncedId = +denouncedId; // Convierte a número
      }
    });

    // Obtener el ID del usuario denunciante del almacenamiento local
    const complainantId = localStorage.getItem('userId');
    if (complainantId) {
      this.report.complainantId = +complainantId; // Convierte a número
    }
  }

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
