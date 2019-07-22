package com.task.solution.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name = "Author")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int authorId;

	@Column(name = "AuthorName")
	private String authorName;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "books_authors", joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "authorId"), inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "bookId"))
	private List<Book> books = new ArrayList<>();

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "comics_authors", joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "authorId"), inverseJoinColumns = @JoinColumn(name = "comic_id", referencedColumnName = "comicId"))
	private List<Comic> comics = new ArrayList<>();

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "magazines_authors", joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "authorId"), inverseJoinColumns = @JoinColumn(name = "magazine_id", referencedColumnName = "magazineId"))
	private List<Magazine> magazines = new ArrayList<>();

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<Comic> getComics() {
		return comics;
	}

	public void setComics(List<Comic> comics) {
		this.comics = comics;
	}

	public List<Magazine> getMagazines() {
		return magazines;
	}

	public void setMagazines(List<Magazine> magazines) {
		this.magazines = magazines;
	}

	public void addBook(Book book) {
		this.books.add(book);
		book.getAuthors().add(this);
	}

	public void removeBook(Book book) {
		this.books.remove(book);
		book.getAuthors().remove(this);
	}

	public void addComic(Comic comic) {
		this.comics.add(comic);
		comic.getAuthors().add(this);
	}

	public void removeComic(Comic comic) {
		this.comics.remove(comic);
		comic.getAuthors().remove(this);
	}

	public void addMagazine(Magazine magazine) {
		this.magazines.add(magazine);
		magazine.getAuthors().add(this);
	}

	public void removeMagazine(Magazine magazine) {
		this.magazines.remove(magazine);
		magazine.getAuthors().remove(this);
	}
}
