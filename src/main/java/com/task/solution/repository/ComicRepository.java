package com.task.solution.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.task.solution.model.Comic;

/**
 * Data JPA repository for Comic entity.
 * @author Naveen Kumar
 *
 */
@Transactional
@Repository
public interface ComicRepository extends CrudRepository<Comic, Integer> {

	public List<Comic> findAll();

	public List<Comic> findByAuthors_AuthorNameAndYear(String authorName, int year);

	public List<Comic> findByAuthors_AuthorName(String authorName);

	public List<Comic> findByYear(int year);

	public List<Comic> findByHero(String hero);

	public List<Comic> findByAuthors_AuthorNameAndYearAndHero(String authorName, int year, String hero);

	public List<Comic> findByAuthors_AuthorNameAndHero(String authorName, String hero);

}
