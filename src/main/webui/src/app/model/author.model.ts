import { Magazine } from './magazine.model';
import { authorId, authorName } from './author.model';
import { Book } from './book.model';
import { Comic } from './comic.mode';
export type authorId = number;
export type authorName = string;

export class Author {

  public authorId: authorId;
  public authorName: authorName;
  public books: Book[];
  public comics: Comic[];
  public magazines: Magazine[];


  public static fromJSON(json: any): Author {
    const authorModel = new Author();
    authorModel.authorId = json.authorId;
    authorModel.authorName = json.authorName;
    authorModel.books = json.books;
    authorModel.comics = json.comics;
    authorModel.magazines = json.magazines;
    return authorModel;
  }

}
