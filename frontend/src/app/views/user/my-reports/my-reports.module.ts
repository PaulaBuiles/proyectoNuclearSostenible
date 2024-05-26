import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyReportsRoutingModule } from './my-reports-routing.module';
import { MyReportsComponent } from './my-reports.component';


@NgModule({
  declarations: [
    MyReportsComponent
  ],
  imports: [
    CommonModule,
    MyReportsRoutingModule
  ]
})
export class MyReportsModule { }
