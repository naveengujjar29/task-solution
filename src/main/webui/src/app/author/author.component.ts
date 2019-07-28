import { Author } from './../model/author.model';
import { AuthorService } from './../services/author.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

  authorData: Author[];

  constructor(private authorService: AuthorService) { }

  ngOnInit() {
    this.authorService.getAuthors().subscribe(
      (response) => {
        console.log(response);
        this.authorData = response;
      },
      err => {
        console.log(err);
      }
    );
    console.table(this.authorData);
  }

}
