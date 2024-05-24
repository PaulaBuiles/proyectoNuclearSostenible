import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './views/login/login.component';
import { ProductsComponent } from './views/products/products.component';
import { NewProductComponent } from './views/new-product/new-product.component';
import { NewPublicationComponent } from './views/new-publication/new-publication.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'products', component: ProductsComponent },
  { path: 'user', loadChildren: () => import('./views/user/user.module').then(m => m.UserModule)},
  { path: 'new-product', component: NewProductComponent},
  { path: 'new-publication', component: NewPublicationComponent },

  // Agrega más rutas aquí si es necesario
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
