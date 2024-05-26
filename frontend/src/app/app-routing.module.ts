import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './views/login/login.component';
import { ProductsComponent } from './views/products/products.component';
import { NewProductComponent } from './views/new-product/new-product.component';
import { NewPublicationComponent } from './views/new-publication/new-publication.component';
import { CreateReportComponent } from './views/create-report/create-report.component';
import { EditProductComponent } from './views/edit-product/edit-product.component';
import { PublicationsComponent } from './views/publications/publications.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'products', component: ProductsComponent },
  { path: 'user', loadChildren: () => import('./views/user/user.module').then(m => m.UserModule)},
  { path: 'new-product', component: NewProductComponent},
  { path: 'new-publication', component: NewPublicationComponent },
  { path: 'new-report', component: CreateReportComponent },
  { path: 'edit-product/:id', component: EditProductComponent},
  { path: 'publications', component: PublicationsComponent},

  // Agrega más rutas aquí si es necesario
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
