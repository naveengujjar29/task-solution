package com.task.solution.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookId")
public class BookDto {

	private int bookId;

	private String title;
	
	private int year;

	private String genre;

	@JsonBackReference
	private Set<AuthorDto> authors = new HashSet<>();

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@JsonIgnore
	public Set<AuthorDto> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<AuthorDto> authors) {
		this.authors = authors;
	}

}
