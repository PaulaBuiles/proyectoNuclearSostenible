import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OfferDto } from '../../../model/offer-dto.model';
import { PublicationInfoService } from '../../../service/publication-info.service';

@Component({
  selector: 'app-my-product-offers',
  templateUrl: './my-product-offers.component.html',
  styleUrls: ['./my-product-offers.component.css']
})
export class MyProductOffersComponent implements OnInit {

  offers: OfferDto[] | null = null;
  newOffer: Partial<OfferDto> = {};
  window: any;

  constructor(
    private route: ActivatedRoute,
    private publicationInfoService: PublicationInfoService
  ) { }

  ngOnInit(): void {
    const publicationId = this.route.snapshot.paramMap.get('id');
    if (publicationId) {
      this.loadOffers(Number(publicationId));
      this.newOffer.publicationId = Number(publicationId); // Asociar la nueva oferta con la publicación actual
    }
  }

  loadOffers(publicationId: number): void {
    this.publicationInfoService.getOffersByPublication(publicationId).subscribe(offers => {
      this.offers = offers;
    });
  }

  onSubmit(): void {
    this.newOffer.offerDate = new Date();
    this.publicationInfoService.createOffer(this.newOffer as OfferDto).subscribe(
      response => {
        this.offers?.push(response); // Agregar la nueva oferta a la lista existente
        this.newOffer = {}; // Limpiar el formulario
      },
      error => {
        console.error('Error creating offer:', error);
      }
    );
    window.location.reload();
  }

  acceptOffer(offerId: number): void {
    this.publicationInfoService.acceptOffer(offerId).subscribe(response => {
      console.log('Oferta aceptada:', response);
      
    }, error => {
      console.error('Error al aceptar la oferta:', error);
    });
    
  }

  rejectOffer(offerId: number): void {
    this.publicationInfoService.rejectOffer(offerId).subscribe(response => {
      console.log('Oferta rechazada:', response);
    }, error => {
      console.error('Error al rechazar la oferta:', error);
    });
    
  }
  
}
