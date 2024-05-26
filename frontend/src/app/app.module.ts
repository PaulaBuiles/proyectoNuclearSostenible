import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './views/login/login.component';
import { ReactiveFormsModule } from '@angular/forms'; // Importa ReactiveFormsModule
import { HttpClientModule } from '@angular/common/http';
import { ProductsComponent } from './views/products/products.component';
import { NavbarComponent } from './utils/navbar/navbar.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './utils/token.interceptor';
import { NewProductComponent } from './views/new-product/new-product.component';
import { NewPublicationComponent } from './views/new-publication/new-publication.component';
import { CreateReportComponent } from './views/create-report/create-report.component';
import { EditProductComponent } from './views/edit-product/edit-product.component';
import { PublicationsComponent } from './views/publications/publications.component'; // Ajusta la ruta según tu estructura de proyecto




@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ProductsComponent,
    NavbarComponent,
    NewProductComponent,
    NewPublicationComponent,
    CreateReportComponent,
    EditProductComponent,
    PublicationsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
