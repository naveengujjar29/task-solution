import { authorName, authorId } from './../../model/author.model';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { AuthorService } from './../../services/author.service';
import { BookService } from './../../services/book.service';
import { Book } from './../../model/book.model';
import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { Author } from 'src/app/model/author.model';
import { MatAutocompleteSelectedEvent, MatAutocomplete, MatChipInputEvent } from '@angular/material';
import { Observable } from 'rxjs';
import { FormControl } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})
export class AddBookComponent implements OnInit {
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  fruitCtrl = new FormControl();
  filteredFruits: Observable<String[]>;
  fruits: string[] = ['Lemon'];
  allFruits: string[] = ['Apple', 'Lemon', 'Lime', 'Orange', 'Strawberry'];
  selectedAuthors: String[] = new Array();

  allAuthor: Author[];

  @ViewChild('fruitInput', { static: false }) fruitInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto', { static: false }) matAutocomplete: MatAutocomplete;

  book: Book = new Book();
  authors: Author[];
  authorId:number;
  authorTmp:Author = new Author();

  constructor(private authorService: AuthorService, private bookService: BookService, public dialogRef: MatDialogRef<AddBookComponent>) {
     this.fruitCtrl.valueChanges.pipe(
      startWith(''),
      map(fruit => fruit ? this._filter(fruit) : this.selectedAuthors.slice())).subscribe();
  }

  ngOnInit() {
    this.authorService.getAuthors().subscribe(
      (response) => {
        console.log(response);
        this.allAuthor = response;
      },
      err => {
        console.log(err);
      }
    );
  }

  add(event: MatChipInputEvent): void {
    // Add fruit only when MatAutocomplete is not open
    // To make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete.isOpen) {
      const input = event.input;
      const value = event.value;

      // Add our fruit
      if ((value || '').trim()) {
        this.fruits.push(value.trim());
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }

      this.fruitCtrl.setValue(null);
    }
  }

  remove(author: string): void {
    const index = this.selectedAuthors.indexOf(author);
    if (index >= 0) {
      this.book.authors.splice(index, 1);
      this.selectedAuthors.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const selectedAuthorId: number = +event.option.value;
    const author = new Author();
    author.authorId = selectedAuthorId;
    this.book.authors.push(author);
    this.selectedAuthors.push(event.option.viewValue);
    this.fruitInput.nativeElement.value = event.option.value;
    this.fruitInput.nativeElement.value = '';
    this.fruitCtrl.setValue(null);
    console.log(this.book);
  }

  private _filter(value: string) {
    console.log("---------" + value);
    //const filterValue = value.toLowerCase();
    console.log(value);
    //return this.allAuthor.filter(author => author.indexOf(value) === 0);
  }

  close() {
    this.dialogRef.close();
  }

   addBook() {
    this.bookService.addBook(JSON.stringify(this.book)).subscribe();
    this.dialogRef.close();
  }

}
