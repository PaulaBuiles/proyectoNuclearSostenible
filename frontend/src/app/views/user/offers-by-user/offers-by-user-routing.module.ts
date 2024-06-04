import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OffersByUserComponent } from './offers-by-user.component';

const routes: Routes = [{ path: '', component: OffersByUserComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OffersByUserRoutingModule { }
