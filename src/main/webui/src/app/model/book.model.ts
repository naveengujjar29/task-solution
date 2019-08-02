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
  public authors: Author[] = new Array();

  public static fromJSON(json: any): Book {
    const bookModel = new Book();
    bookModel.bookId = json.bookId;
    bookModel.title = json.title;
    bookModel.year = json.year;
    bookModel.genre = json.genre;
    bookModel.authors = json.authors;
    return bookModel;
  }


}
