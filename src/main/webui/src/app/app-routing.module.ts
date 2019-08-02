import { BookComponent } from './book/book.component';
import { RouterModule, Routes } from '@angular/router';

import { NgModule } from '@angular/core';
import { AuthorComponent } from './author/author.component';

const routes: Routes = [
  { path: '', redirectTo: 'authors', pathMatch: 'full' },
  { path: 'authors', component: AuthorComponent },
  { path: 'books', component: BookComponent }
  //{path: '**}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
