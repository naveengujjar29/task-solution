import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Author } from '../model/author.model';
import { map, mapTo } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  constructor(private httpClient: HttpClient) { }

  getAuthors(): Observable<Author[]> {
    const authorURI = 'http://localhost:8085/authors';
    const options = {
      headers: new HttpHeaders({ 'content-type': 'application/json' }),
    };
    return this.httpClient.get<any>(authorURI, options).pipe(
      map((data) => {
        return data.map(Author.fromJSON);
      }
      ));
  }


}
