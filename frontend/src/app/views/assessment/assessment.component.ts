import { Component } from '@angular/core';
import { PublicationInfoService } from '../../service/publication-info.service';
import { AssessmentDto } from '../../model/assessment-dto.model';

@Component({
  selector: 'app-assessment',
  templateUrl: './assessment.component.html',
  styleUrls: ['./assessment.component.css']
})
export class AssessmentComponent {
  assessment: AssessmentDto = {
    transactionId: 1,
    rating: 5,
    comment: 'Excellent service!'
  };

  constructor(private publicationInfoService: PublicationInfoService) { }

  addAssessment(): void {
    this.publicationInfoService.addAssessment(this.assessment).subscribe(response => {
      console.log('Assessment added successfully:', response);
    }, error => {
      console.error('Error adding assessment:', error);
    });
  }
}
