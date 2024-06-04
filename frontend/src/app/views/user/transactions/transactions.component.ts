import { Component } from '@angular/core';
import { OfferDto } from '../../../model/offer-dto.model';
import { PublicationInfoService } from '../../../service/publication-info.service';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.css'
})
export class TransactionsComponent {
  offers: OfferDto[] | null = null;
  showAssessmentForm: boolean = false; // Añadir esta línea

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
    this.offers = offers.filter(offer => !!offer.transactionId);
    });
  }

  toggleAssessmentForm(): void {
    this.showAssessmentForm = !this.showAssessmentForm;
  }
}
