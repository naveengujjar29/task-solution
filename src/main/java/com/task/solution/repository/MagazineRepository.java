package com.task.solution.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.solution.model.Magazine;

public interface MagazineRepository extends JpaRepository<Magazine, Integer> {

	public List<Magazine> findAll();

	public List<Magazine> findByAuthors_AuthorNameAndYear(String authorName, int year);

	public List<Magazine> findByAuthors_AuthorName(String authorName);

	public List<Magazine> findByYear(int year);

	public List<Magazine> findByMagazineType(String magazineType);

	public List<Magazine> findByAuthors_AuthorNameAndYearAndMagazineType(String authorName, int year,
			String magazineType);

	public List<Magazine> findByAuthors_AuthorNameAndMagazineType(String authorName, String magazineType);

}
