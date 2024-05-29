import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyProductRoutingModule } from './my-product-routing.module';
import { MyProductComponent } from './my-product.component';


@NgModule({
  declarations: [
    MyProductComponent
  ],
  imports: [
    CommonModule,
    MyProductRoutingModule
  ]
})
export class MyProductModule { }
