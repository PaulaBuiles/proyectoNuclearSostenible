import { Component } from '@angular/core';
import { ProductService } from '../../service/product.service';
import { ProductDto } from '../../model/product-dto.model';

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.css']
})
export class NewProductComponent {

  product: ProductDto = {
    idProduct: 0,
    name: '',
    price: 0,
    imageUrl: '',
    description: '',
    userId: 0,
    categoryId: 0,
    status: true
  };

  constructor(private productService: ProductService) { }

  createProduct(): void {
    this.productService.createProduct(this.product).subscribe(
      response => {
        console.log('Product created successfully', response);
      },
      error => {
        console.error('Error creating product', error);
      }
    );
  }
}
