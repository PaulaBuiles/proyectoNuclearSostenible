import { Component, OnInit } from '@angular/core';
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
  searchQuery: string = '';

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe(
      (data: ProductDto[]) => {
        console.log(data);
        this.products = data;
        this.filteredProducts = data;
      },
      (error: any) => {
        console.error('Error fetching products', error);
      }
    );
  }

  onSearch(): void {
    this.filteredProducts = this.products.filter(product =>
      product.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
      product.description.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }

  onFilterChange(filterType: string, value: string): void {
    // Implementa lógica para filtrar los productos según el tipo de filtro y el valor seleccionado
    console.log(`Filter Type: ${filterType}, Value: ${value}`);
  }
}
