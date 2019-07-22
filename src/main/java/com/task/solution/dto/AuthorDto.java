package com.task.solution.dto;

import java.io.Serializable;
import java.util.List;


public class AuthorDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int authorId;

	private String authorName;
	
	private List<BookDto> books;

	private List<MagazineDto> magazines;
	
	private List<ComicDto> comics;

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

	public List<BookDto> getBooks() {
		return books;
	}

	public void setBooks(List<BookDto> books) {
		this.books = books;
	}

	public List<MagazineDto> getMagazines() {
		return magazines;
	}

	public void setMagazines(List<MagazineDto> magazines) {
		this.magazines = magazines;
	}

	public List<ComicDto> getComics() {
		return comics;
	}

	public void setComics(List<ComicDto> comics) {
		this.comics = comics;
	}

}
