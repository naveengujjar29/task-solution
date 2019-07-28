import { Author } from './author.model';

export type comicId = number;
export type title = string;
export type year = number;
export type magazineType = string;

export class Magazine {

  public comicId: comicId;
  public title: title;
  public year: year;
  public magazineType: magazineType;
  public authors: Author[];



}
