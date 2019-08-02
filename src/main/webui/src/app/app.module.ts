import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { AppMaterialModule } from './app-material/app-material.module';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthorComponent } from './author/author.component';
import { MatCardModule } from '@angular/material/card';
import { HttpClientModule } from '@angular/common/http';
import { AddAuthorComponent } from './modals/add-author/add-author.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BookComponent } from './book/book.component';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { AddBookComponent } from './modals/add-book/add-book.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthorComponent,
    AddAuthorComponent,
    BookComponent,
    AddBookComponent
  ],
  imports: [
    AppMaterialModule,
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    AngularFontAwesomeModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [AddAuthorComponent, AddBookComponent]
})
export class AppModule { }
