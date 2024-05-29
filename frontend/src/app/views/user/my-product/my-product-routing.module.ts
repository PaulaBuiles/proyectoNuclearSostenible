import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyProductComponent } from './my-product.component';

const routes: Routes = [{ path: '', component: MyProductComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyProductRoutingModule { }
