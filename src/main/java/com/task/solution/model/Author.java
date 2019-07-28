package com.task.solution.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "authorId")
@Entity(name = "Author")
@JsonIgnoreProperties("authors")
public class Author implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int authorId;

	@Column(name = "AuthorName")
	private String authorName;

	@JsonIgnoreProperties("authors")
	@ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "books_authors", joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "authorId"), inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "bookId"))
	private Set<Book> books = new HashSet<>();

	@JsonIgnoreProperties("authors")
	@ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "comics_authors", joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "authorId"), inverseJoinColumns = @JoinColumn(name = "comic_id", referencedColumnName = "comicId"))
	private Set<Comic> comics = new HashSet<>();

	@JsonIgnoreProperties("authors")
	@ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "magazines_authors", joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "authorId"), inverseJoinColumns = @JoinColumn(name = "magazine_id", referencedColumnName = "magazineId"))
	private Set<Magazine> magazines = new HashSet<>();

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

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public Set<Comic> getComics() {
		return comics;
	}

	public void setComics(Set<Comic> comics) {
		this.comics = comics;
	}

	public Set<Magazine> getMagazines() {
		return magazines;
	}

	public void setMagazines(Set<Magazine> magazines) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + authorId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		if (authorId != other.authorId)
			return false;
		return true;
	}

}
