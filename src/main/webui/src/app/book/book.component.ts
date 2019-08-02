import { AddBookComponent } from './../modals/add-book/add-book.component';
import { MatDialog } from '@angular/material';
import { Book } from './../model/book.model';
import { BookService } from './../services/book.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  books: Book[];
  constructor(private bookService: BookService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getBooks();
  }

  addBook() {
    const dialogRef = this.dialog.open(AddBookComponent, {
      disableClose: true,
      width: '500px'
    });
    this.dialog.afterAllClosed.subscribe((data: any) => this.getBooks());
  }


  getBooks() {
    this.bookService.getBooksList().subscribe(
      (response) => {

        this.books = response;
      },
      (err) => {
        console.log(err);
      }
    );
  }

}
