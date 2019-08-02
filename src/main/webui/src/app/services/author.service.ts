import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Author } from '../model/author.model';
import { map, mapTo } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  authorURI = 'http://localhost:8085/authors';

  constructor(private httpClient: HttpClient) { }

  getAuthors(): Observable<Author[]> {

    const options = {
      headers: new HttpHeaders({ 'content-type': 'application/json' }),
    };
    return this.httpClient.get<any>(this.authorURI, options).pipe(
      map((data) => {
        return data.map(Author.fromJSON);
      }
      ));
  }

  postAuthor(authorData: string): Observable<any> {
    const options = {
      headers: new HttpHeaders({ 'content-type': 'application/json' }),
    };
    return this.httpClient.post(this.authorURI, authorData, options);

  }

  deleteAuthor(authorId: number): Observable<any> {

    return this.httpClient.delete(this.authorURI + '/' + authorId);

  }


}
