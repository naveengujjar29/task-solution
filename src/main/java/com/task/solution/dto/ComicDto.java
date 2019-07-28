package com.task.solution.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "comicId")
@JsonInclude(Include.NON_NULL)
public class ComicDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int comicId;

	private String title;

	private int year;

	private String hero;

	@JsonIgnoreProperties("comics")
	private Set<AuthorDto> authors = new HashSet<>();

	public int getComicId() {
		return comicId;
	}

	public void setComicId(int comicId) {
		this.comicId = comicId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getHero() {
		return hero;
	}

	public void setHero(String hero) {
		this.hero = hero;
	}

	public Set<AuthorDto> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<AuthorDto> authors) {
		this.authors = authors;
	}

}
