import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [{ path: 'profile', loadChildren: () => import('./profile/profile.module').then(m => m.ProfileModule) }, 
{ path: 'my-reports', loadChildren: () => import('./my-reports/my-reports.module').then(m => m.MyReportsModule) }, 
{ path: 'my-product/:id', loadChildren: () => import('./my-product/my-product.module').then(m => m.MyProductModule) },
{ path: 'my-product-offers/:id', loadChildren: () => import('./my-product-offers/my-product-offers.module').then(m => m.MyProductOffersModule) }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
