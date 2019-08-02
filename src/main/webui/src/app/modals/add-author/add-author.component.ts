import { Author } from './../../model/author.model';
import { AuthorService } from './../../services/author.service';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-author',
  templateUrl: './add-author.component.html',
  styleUrls: ['./add-author.component.css']
})
export class AddAuthorComponent implements OnInit {

  author = new Author();

  constructor(public dialogRef: MatDialogRef<AddAuthorComponent>, private authorService: AuthorService, private router: Router) { }

  ngOnInit() {
  }

  close() {
    this.dialogRef.close();
  }


  addAuthor() {
    console.log(JSON.stringify(this.author));
    this.authorService.postAuthor(JSON.stringify(this.author)).subscribe();
    this.dialogRef.close();
  }



}
