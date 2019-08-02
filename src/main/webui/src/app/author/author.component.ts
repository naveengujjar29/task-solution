import { AddAuthorComponent } from './../modals/add-author/add-author.component';
import { Author } from './../model/author.model';
import { AuthorService } from './../services/author.service';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

  authorData: Author[];

  constructor(private authorService: AuthorService, private dialog: MatDialog) { }

  ngOnInit() {
    this.getAuthors();
  }

  getAuthors() {
    this.authorService.getAuthors().subscribe(
      (response) => {
        console.log(response);
        this.authorData = response;
      },
      err => {
        console.log(err);
      }
    );
  }

  addAuthor() {
    const dialogRef = this.dialog.open(AddAuthorComponent, {
      disableClose: true,
      width: '500px'
    });
    this.dialog.afterAllClosed.subscribe((data: any) => this.getAuthors());
  }

  deleteAuthor(authorId: number) {
    this.authorService.deleteAuthor(authorId).subscribe((resp) => {
      this.getAuthors();
    });

  }

}
