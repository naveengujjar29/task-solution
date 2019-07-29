package com.task.solution.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO class for Author.
 * @author Naveen Kumar
 *
 */
@JsonInclude(Include.NON_NULL)
public class AuthorDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int authorId;

	private String authorName;

	@JsonIgnoreProperties("authors")
	private Set<BookDto> books = new HashSet<>();

	@JsonIgnoreProperties("authors")
	private Set<MagazineDto> magazines = new HashSet<>();

	@JsonIgnoreProperties("authors")
	private Set<ComicDto> comics = new HashSet<>();;

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

	public Set<BookDto> getBooks() {
		return books;
	}

	public void setBooks(Set<BookDto> books) {
		this.books = books;
	}

	public Set<MagazineDto> getMagazines() {
		return magazines;
	}

	public void setMagazines(Set<MagazineDto> magazines) {
		this.magazines = magazines;
	}

	public Set<ComicDto> getComics() {
		return comics;
	}

	public void setComics(Set<ComicDto> comics) {
		this.comics = comics;
	}

}
