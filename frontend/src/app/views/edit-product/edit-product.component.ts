import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { ProductService } from '../../service/product.service';
import { ProductDto } from '../../model/product-dto.model';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  product: ProductDto | null = null;
  productId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.productId = +id;
        this.loadProduct(this.productId);
      }
    });
  }

  loadProduct(id: number): void {
    this.productService.getProductById(id).subscribe(
      (product: ProductDto) => {
        this.product = product;
      },
      error => {
        console.error('Error fetching product', error);
      }
    );
  }

  onSubmit(form: NgForm): void {
  }
}
