import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AssessmentComponent } from './assessment.component';
import { FormsModule } from '@angular/forms'; // Asegúrate de importar FormsModule



@NgModule({
  declarations: [
    AssessmentComponent,
    // Otros componentes que puedas tener en este módulo
  ],
  imports: [
    CommonModule,
    FormsModule,
  ],
  exports: [
    AssessmentComponent // Exporta el componente del carrusel para que otros módulos puedan usarlo
  ]
})
export class AssessmentModule { }
