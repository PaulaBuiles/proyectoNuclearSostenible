import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublicationInfoService } from '../../service/publication-info.service';
import { ProductService } from '../../service/product.service';
import { OfferDto } from '../../model/offer-dto.model';
import { ProductDto } from '../../model/product-dto.model';

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent implements OnInit {
  showOfferForm = false;
  showMonetaryValue = false;
  offers: OfferDto[] | null = null;
  newOffer: Partial<OfferDto> = {};
  userProducts: ProductDto[] = [];

  constructor(
    private route: ActivatedRoute,
    private publicationInfoService: PublicationInfoService,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
    const publicationId = this.route.snapshot.paramMap.get('publicationId');
    const userId = Number(localStorage.getItem('userId')); // Supongamos que tienes el userId almacenado en localStorage

    if (publicationId) {
      this.loadOffers(Number(publicationId));
      this.newOffer.publicationId = Number(publicationId); // Asociar la nueva oferta con la publicación actual
    }

    if (userId) {
      this.loadUserProducts(userId);
    }
  }

  loadOffers(publicationId: number): void {
    this.publicationInfoService.getOffersByPublication(publicationId).subscribe(offers => {
      this.offers = offers;
    });
  }

  loadUserProducts(userId: number): void {
    this.productService.getProductsByUserId(userId).subscribe(products => {
      this.userProducts = products;
    });
  }

  onProductChange(event: any): void {
    this.showMonetaryValue = event.target.value === 'none';
    if (!this.showMonetaryValue) {
      this.newOffer.monetaryValue = 0; // Resetear el valor monetario si no se intercambia producto
    }
  }

  onSubmit(): void {
    this.newOffer.offerDate = new Date();
    this.newOffer.offererId = Number(localStorage.getItem('userId'));
    if(this.newOffer.monetaryValue !== 0){
      this.newOffer.exchangedProductId = undefined;
    }
    this.publicationInfoService.createOffer(this.newOffer as OfferDto).subscribe(
      response => {
        this.offers?.push(response); 
      },
      error => {
        console.error('Error creating offer:', error);
      }
    );
    window.location.reload();
  }

  toggleOfferForm() {
    this.showOfferForm = !this.showOfferForm;
  }
}
