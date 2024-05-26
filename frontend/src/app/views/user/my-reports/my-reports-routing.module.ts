import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MyReportsComponent } from './my-reports.component';

const routes: Routes = [{ path: '', component: MyReportsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyReportsRoutingModule { }
