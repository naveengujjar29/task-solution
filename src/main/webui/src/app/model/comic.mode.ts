import { Author } from './author.model';

export type comicId = number;
export type title = string;
export type year = number;
export type hero = string;

export class Comic {

  public comicId: comicId;
  public title: title;
  public year: year;
  public hero: hero;
  public authors: Author[];



}
