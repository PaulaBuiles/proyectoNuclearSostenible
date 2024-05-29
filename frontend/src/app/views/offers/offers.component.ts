import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublicationInfoService } from '../../service/publication-info.service';
import { OfferDto } from '../../model/offer-dto.model';

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent implements OnInit {

  offers: OfferDto[] | null = null;
  newOffer: Partial<OfferDto> = {};
  window: any;

  constructor(
    private route: ActivatedRoute,
    private publicationInfoService: PublicationInfoService
  ) { }

  ngOnInit(): void {
    const publicationId = this.route.snapshot.paramMap.get('publicationId');
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
}