import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
  showReportForm: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private publicationInfoService: PublicationInfoService
  ) { }

  ngOnInit(): void {
    const productId = this.route.snapshot.paramMap.get('id');
    if (productId) {
      this.loadPublicationDetails(Number(productId));
    }
  }

  loadPublicationDetails(productId: number): void {
  console.log(productId);
    this.publicationInfoService.getPublicationByProductId(productId).subscribe(publication => {
      this.publication = publication;
      if (publication) {
        this.publicationInfoService.getProductById(productId).subscribe(product => {
          this.product = product;
          if (product) {
            const userId = product.user ? product.user.idUser : undefined;
            if (userId) {
              localStorage.setItem('denoucedId', userId.toString());
              this.publicationInfoService.getUserById(userId).subscribe(user => {
                this.user = user;
              });
            }
          }
        });
      }
    });
  }  

  toggleReportForm(): void {
    this.showReportForm = !this.showReportForm;
  }

  viewOffers(publicationId: number): void {
    this.router.navigate(['/offers', publicationId]);
  }

  reportUser(userId: number): void {
    this.toggleReportForm(); // Mostrar el formulario de reporte al hacer clic en el botón "Reportar"
  }
}
