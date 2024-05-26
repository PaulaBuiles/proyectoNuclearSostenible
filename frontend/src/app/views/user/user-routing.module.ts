import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [{ path: 'profile', loadChildren: () => import('./profile/profile.module').then(m => m.ProfileModule) }, { path: 'my-reports', loadChildren: () => import('./my-reports/my-reports.module').then(m => m.MyReportsModule) }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
