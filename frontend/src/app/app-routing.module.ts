import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './views/login/login.component';
import { ProductsComponent } from './views/products/products.component';
import { NewProductComponent } from './views/new-product/new-product.component';
import { NewPublicationComponent } from './views/new-publication/new-publication.component';
import { CreateReportComponent } from './views/create-report/create-report.component';
import { EditProductComponent } from './views/edit-product/edit-product.component';
import { LandingCircularEconomyComponent } from './utils/landing-circular-economy/landing-circular-economy.component';
import { PublicationComponent } from './views/publication/publication.component';
import { OffersComponent } from './views/offers/offers.component';
import { CircularEconomyComponent } from './views/circular-economy/circular-economy.component';
import { RewardsComponent } from './views/rewards/rewards.component';
import { CouponComponent } from './views/coupon/coupon.component';



const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'products', component: ProductsComponent },
  { path: 'user', loadChildren: () => import('./views/user/user.module').then(m => m.UserModule)},
  { path: 'new-product', component: NewProductComponent},
  { path: 'new-publication/:id', component: NewPublicationComponent },
  { path: 'new-report/:id', component: CreateReportComponent },
  { path: 'edit-product/:id', component: EditProductComponent},
  { path: 'landing', component: LandingCircularEconomyComponent},
  { path: 'publication/:id', component: PublicationComponent },
  { path: 'offers/:publicationId', component: OffersComponent },
  { path: 'circularEconomy', component: CircularEconomyComponent},
  { path: 'rewards', component: RewardsComponent},
  { path: 'coupon', component: CouponComponent},


  // Agrega más rutas aquí si es necesario
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
