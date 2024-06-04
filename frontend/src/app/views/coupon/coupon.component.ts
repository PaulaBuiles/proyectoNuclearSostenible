import { Component, OnInit } from '@angular/core';
import { CouponService } from '../../service/coupon.service';
import { CouponDto } from '../../model/coupon-dto.model';

@Component({
  selector: 'app-coupon',
  templateUrl: './coupon.component.html',
  styleUrls: ['./coupon.component.css']
})
export class CouponComponent implements OnInit {

  coupon: CouponDto[] = [];
  errorMessage: string | null = null;

  constructor(private couponService: CouponService) {}

  ngOnInit(): void {
    this.loadCoupon();
  }

  loadCoupon(): void {
    this.couponService.getAllCoupons().subscribe(
      (data: CouponDto[]) => {
        this.coupon = data;
        console.log(this.coupon);
      },
      (error: any) => {
        this.errorMessage = 'Error fetching coupon';
        console.error('Error fetching coupon', error);
      }
    );
  }

  redeemCoupon(couponId: number): void {
    const userId = localStorage.getItem("userId"); // Obtén el userId de algún lugar, como el servicio de autenticación
    if (userId !== null) {
      this.couponService.purchaseCoupon(Number(userId), couponId).subscribe(
        response => {
          console.log('Cupón canjeado con éxito', response);
          this.loadCoupon(); // Recargar los cupones después de canjear uno
        },
        error => {
          this.errorMessage = 'Error canjeando el cupón';
          console.error('Error canjeando el cupón', error);
        }
      );
    } else {
      this.errorMessage = 'Error canjeando el cupón: userId es nulo';
      console.error('Error canjeando el cupón: userId es nulo');
    }
  }
}
