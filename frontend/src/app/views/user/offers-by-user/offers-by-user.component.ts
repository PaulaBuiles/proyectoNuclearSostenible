import { Component } from '@angular/core';
import { OfferDto } from '../../../model/offer-dto.model';
import { PublicationInfoService } from '../../../service/publication-info.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-offers-by-user',
  templateUrl: './offers-by-user.component.html',
  styleUrl: './offers-by-user.component.css'
})
export class OffersByUserComponent {
  offers: OfferDto[] | null = null;

  constructor(
    private publicationInfoService: PublicationInfoService
  ) { }

  ngOnInit(): void {
    const publicationId = localStorage.getItem('userId');
    if (publicationId) {
      this.loadOffers(Number(publicationId));
    }
  }

  loadOffers(publicationId: number): void {
    this.publicationInfoService.getOffersByUserId(publicationId).subscribe(offers => {
      this.offers = offers.filter(offer => !offer.transactionId);
    });
  }
}
