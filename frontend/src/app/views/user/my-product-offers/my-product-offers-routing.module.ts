import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyProductOffersComponent } from './my-product-offers.component';

const routes: Routes = [{ path: '', component: MyProductOffersComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyProductOffersRoutingModule { }
