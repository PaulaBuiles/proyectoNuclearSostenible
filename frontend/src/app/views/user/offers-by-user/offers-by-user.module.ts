import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OffersByUserRoutingModule } from './offers-by-user-routing.module';
import { OffersByUserComponent } from './offers-by-user.component';


@NgModule({
  declarations: [
    OffersByUserComponent
  ],
  imports: [
    CommonModule,
    OffersByUserRoutingModule
  ]
})
export class OffersByUserModule { }
