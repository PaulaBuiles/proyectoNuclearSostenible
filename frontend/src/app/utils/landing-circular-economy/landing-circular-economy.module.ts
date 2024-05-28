import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LandingCircularEconomyComponent } from './landing-circular-economy.component'; // Asegúrate de ajustar la ruta

@NgModule({
  declarations: [
    LandingCircularEconomyComponent,
    // Otros componentes que puedas tener en este módulo
  ],
  imports: [
    CommonModule
  ],
  exports: [
    LandingCircularEconomyComponent // Exporta el componente del carrusel para que otros módulos puedan usarlo
  ]
})
export class LandingCircularEconomyModule { }
