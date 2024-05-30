import { Component } from '@angular/core';
import { PublicationService } from '../../service/publication.service';
import { PublicationDto } from '../../model/publication-dto.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-new-publication',
  templateUrl: './new-publication.component.html',
  styleUrls: ['./new-publication.component.css']
})
export class NewPublicationComponent {
  publication: any = {
    ownerId: null,
    productId: null,
    title: '',
    dateCreated: '',
    state: {
      idState: null
    }
  };

  constructor(private route: ActivatedRoute, private publicationService: PublicationService) {}

  ngOnInit(): void {
    // Obtener el ownerId del localStorage
    this.publication.ownerId = localStorage.getItem('userId');

    // Obtener el productId de la URL
    this.route.paramMap.subscribe(params => {
      this.publication.productId = params.get('id');
    });
  }

  createPublication(): void {
    if (this.publication.ownerId && this.publication.productId) {
      this.publicationService.createPublication(this.publication).subscribe(response => {
        console.log('Publication created successfully', response);
        // Manejar la respuesta, redirigir al usuario, etc.
      }, error => {
        console.error('Error creating publication', error);
        // Manejar el error
      });
    } else {
      console.error('Owner ID or Product ID is missing');
    }
  }
}
