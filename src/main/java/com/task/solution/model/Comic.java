package com.task.solution.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "Comic")
public class Comic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int comicId;

	@Column(name = "Title")
	private String title;

	@Column(name = "Year")
	private int year;

	@Column(name = "Hero")
	private String hero;
	
	@JsonIgnoreProperties("comics")
	@ManyToMany( mappedBy = "comics", fetch=FetchType.LAZY)
	private Set<Author> authors = new HashSet<>();

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

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

}
