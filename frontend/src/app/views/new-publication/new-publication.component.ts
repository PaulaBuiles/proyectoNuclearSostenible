import { Component } from '@angular/core';
import { PublicationService } from '../../service/publication.service';
import { PublicationDto } from '../../model/publication-dto.model';

@Component({
  selector: 'app-new-publication',
  templateUrl: './new-publication.component.html',
  styleUrls: ['./new-publication.component.css']
})
export class NewPublicationComponent {

  publication: PublicationDto = {
    idPublication: 0,
    ownerId: 0,
    productId: 0,
    title: '',
    dateCreated: new Date(),
    stateId: 0
  };

  constructor(private publicationService: PublicationService) { }

  createPublication(): void {
    console.log(this.publication)
    this.publicationService.createPublication(this.publication).subscribe(
      response => {
        console.log('Publication created successfully', response);
      },
      error => {
        console.error('Error creating publication', error);
      }
    );
  }
}
