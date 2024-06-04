import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router} from '@angular/router';
import { ProductService } from '../../service/product.service';
import { ProductDto } from '../../model/product-dto.model';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: ProductDto[] = [];
  filteredProducts: ProductDto[] = [];
  searchParams: any = {
    title: '',
    productName: '',
    productDescription: '',
    categoryTitle: '',
    stateDescription: ''
  };

  constructor(private productService: ProductService, private router: Router) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe(
      (data: ProductDto[]) => {
        this.products = data;
        this.filteredProducts = data;
        

        // Cargar publicaciones para cada producto
        this.products.forEach(product => {
          this.productService.getPublicationByProductId(product.idProduct).subscribe(
            publication => {
              console.log(product.productCategory);
              product.publication = publication;
            },
            error => {
              console.error('Error fetching publication', error);
            }
          );
        });
      },
      error => {
        console.error('Error fetching products', error);
      }
    );
  }

  onSearch(): void {
    this.filteredProducts = this.products.filter(product => {
      const matchesTitle = this.searchParams.title ? product.publication?.title.toLowerCase().includes(this.searchParams.title.toLowerCase()) : true;
      const matchesProductName = this.searchParams.productName ? product.name.toLowerCase().includes(this.searchParams.productName.toLowerCase()) : true;
      const matchesProductDescription = this.searchParams.productDescription ? product.description.toLowerCase().includes(this.searchParams.productDescription.toLowerCase()) : true;
      const matchesCategoryTitle = this.searchParams.categoryTitle ? product.productCategory?.title.toLowerCase().includes(this.searchParams.categoryTitle.toLowerCase()) : true;
      const matchesStateDescription = this.searchParams.stateDescription ? product.publication?.state.description.toLowerCase().includes(this.searchParams.stateDescription.toLowerCase()) : true;

      return matchesTitle && matchesProductName && matchesProductDescription && matchesCategoryTitle && matchesStateDescription;
    });
  }

  viewPublication(productId: number): void {
    this.router.navigate(['/publication', productId]);
  }
  goToMoreInfo(): void {
    this.router.navigate(['/rewards']);
  }
}