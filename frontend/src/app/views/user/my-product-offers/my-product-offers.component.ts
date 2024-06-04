import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OfferDto } from '../../../model/offer-dto.model';
import { PublicationInfoService } from '../../../service/publication-info.service';
import { forkJoin } from 'rxjs';

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
      const userRequests = offers.map(offer => 
        this.publicationInfoService.getUserById(offer.offererId)
      );
      forkJoin(userRequests).subscribe(users => {
        this.offers = offers.map((offer, index) => {
          return {
            ...offer,
            offererName: users[index].fullName,
            offererPhone: users[index].phone
          };
        });
      });
    });
  }

  acceptOffer(offerId: number): void {
    this.publicationInfoService.acceptOffer(offerId).subscribe(response => {
      console.log('Oferta aceptada:', response);
      
    }, error => {
      console.error('Error al aceptar la oferta:', error);
    });
    window.location.reload();
  }

  rejectOffer(offerId: number): void {
    this.publicationInfoService.rejectOffer(offerId).subscribe(response => {
      console.log('Oferta rechazada:', response);
    }, error => {
      console.error('Error al rechazar la oferta:', error);
    });
    window.location.reload();
  }

  getWhatsAppLink(phoneNumber: string): string {
    const productName = this.getProductName();
    const message = `Hola, como estas? me gusto la oferta que me hiciste para el producto con nombre "${productName}", así que, me gustaría coordinar la entrega.`;
    return `https://wa.me/57${phoneNumber}?text=${encodeURIComponent(message)}`;
  }

  getProductName(): string {
    return localStorage.getItem('product-offers') || '';
  }
}
