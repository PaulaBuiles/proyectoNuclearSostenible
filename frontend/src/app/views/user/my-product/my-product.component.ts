import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PublicationInfoService } from '../../../service/publication-info.service';
import { UserDto } from '../../../model/user-dto.model';
import { ProductDto } from '../../../model/product-dto.model';
import { PublicationDto } from '../../../model/publication-dto.model';

@Component({
  selector: 'app-my-product',
  templateUrl: './my-product.component.html',
  styleUrls: ['./my-product.component.css']
})
export class MyProductComponent implements OnInit {

  product: ProductDto | null = null;
  user: UserDto | null = null;
  publication: PublicationDto | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private publicationInfoService: PublicationInfoService
  ) { }

  ngOnInit(): void {
    const productId = this.route.snapshot.paramMap.get('id');
    if (productId) {
      this.loadProductDetails(Number(productId));
    }
  }

  loadProductDetails(productId: number): void {
    this.publicationInfoService.getProductById(productId).subscribe(product => {
      this.product = product;
      if (product) {
        localStorage.setItem('product-offers', product.name);
        // Comprueba si el producto tiene publicaciones asociadas
        this.publicationInfoService.getPublicationByProductId(productId).subscribe(publication => {
          this.publication = publication;
        });
      }
    });
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.publicationInfoService.getUserById(Number(userId)).subscribe(user => {
        this.user = user;
      });
    }
  }

  viewOffers(publicationId: number): void {
    this.router.navigate(['/user/my-product-offers', publicationId]);
  }

  createPublication(productId: number): void {
    this.router.navigate(['/new-publication', productId]);
  }

  hasPublications(): boolean {
    return this.publication !== null;
  }
}
