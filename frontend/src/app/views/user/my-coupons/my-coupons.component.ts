import { Component } from '@angular/core';
import { CouponDto } from '../../../model/coupon-dto.model';
import { CouponService } from '../../../service/coupon.service';

@Component({
  selector: 'app-my-coupons',
  templateUrl: './my-coupons.component.html',
  styleUrl: './my-coupons.component.css'
})
export class MyCouponsComponent {
  coupon: CouponDto[] = [];
  errorMessage: string | null = null;

  constructor(private couponService: CouponService) {}

  ngOnInit(): void {
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.loadCoupon(userId);
    }
  }

  loadCoupon(userId: string): void {
    this.couponService.getCouponsByUserId(userId).subscribe(
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
    // Implementar lógica para canjear el cupón
    console.log(`Coupon ID ${couponId} redeemed`);
  }
}
