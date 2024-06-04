import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../../service/user.service';
import { ProductService } from '../../../service/product.service'; // Importar ProductService
import { UserDto } from '../../../model/user-dto.model';
import { ProductDto } from '../../../model/product-dto.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user1: UserDto | null = null;
  products: ProductDto[] = []; // Añadir array de productos

  constructor(
    private userService: UserService,
    private productService: ProductService, // Inyectar ProductService
    private router: Router // Inyectar Router
  ) {}

  ngOnInit(): void {
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.userService.getUserById(userId).subscribe(
        (user: UserDto) => {
          this.user1 = user;
          this.loadProducts(user.idUser); // Cargar productos del usuario
        },
        error => {
          console.error('Error fetching user', error);
        }
      );
      this.userService.getRatingByUserId(userId).subscribe(
        (rating: Number) => {
          if (rating){
            if (this.user1) {
              this.user1.rating = rating.valueOf();
              console.log(this.user1.rating);
            }
          }
        },
        error => {
          console.error('Error fetching rating', error);
        }
      );
    }
  }

  loadProducts(userId: number): void {
    this.productService.getProductsByUserId(userId).subscribe(
      (products: ProductDto[]) => {
        this.products = products;
      },
      error => {
        console.error('Error fetching products', error);
      }
    );
  }

  editProduct(productId: number): void {
    this.router.navigate(['/user/my-product', productId]);
  }
}
