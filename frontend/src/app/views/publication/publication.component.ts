import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublicationInfoService } from '../../service/publication-info.service';
import { UserDto } from '../../model/user-dto.model';
import { ProductDto } from '../../model/product-dto.model';
import { PublicationDto } from '../../model/publication-dto.model';

@Component({
  selector: 'app-publication',
  templateUrl: './publication.component.html',
  styleUrls: ['./publication.component.css']
})
export class PublicationComponent implements OnInit {

  publication: PublicationDto | null = null;
  product: ProductDto | null = null;
  user: UserDto | null = null;

  constructor(
    private route: ActivatedRoute,
    private publicationInfoService: PublicationInfoService
  ) { }

  ngOnInit(): void {
    const productId = this.route.snapshot.paramMap.get('id');
    if (productId) {
      this.loadPublicationDetails(Number(productId));
    }
  }

  loadPublicationDetails(productId: number): void {
    this.publicationInfoService.getPublicationByProductId(productId).subscribe(publication => {
      this.publication = publication;
      if (publication) {
        this.publicationInfoService.getProductById(productId).subscribe(product => {
          this.product = product;
          if (product) {
            const userId = product.user ? product.user.idUser : undefined; // Manejo de undefined
            if (userId) {
              this.publicationInfoService.getUserById(userId).subscribe(user => {
                this.user = user;
              });
            }
          }
        });
      }
    });
  }  
}
