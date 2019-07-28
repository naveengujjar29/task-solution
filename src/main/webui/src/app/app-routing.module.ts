import { RouterModule, Routes } from '@angular/router';

import { NgModule } from '@angular/core';
import { AuthorComponent } from './author/author.component';

const routes: Routes = [
  { path: '', component: AuthorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
