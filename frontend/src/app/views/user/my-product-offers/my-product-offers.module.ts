import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Importa FormsModule

import { MyProductOffersRoutingModule } from './my-product-offers-routing.module';
import { MyProductOffersComponent } from './my-product-offers.component';


@NgModule({
  declarations: [
    MyProductOffersComponent
  ],
  imports: [
    CommonModule,
    MyProductOffersRoutingModule,
    FormsModule
  ]
})
export class MyProductOffersModule { }
