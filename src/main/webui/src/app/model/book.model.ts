import { Author } from './author.model';

export type bookId = number;
export type title = string;
export type year = number;
export type genre = string;

export class Book {

  public bookId: bookId;
  public title: title;
  public year: year;
  public genre: genre;
  public authors: Author[];



}
