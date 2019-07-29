package com.task.solution.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.task.solution.model.Book;

/**
 * Data JPA class for Book model.
 * @author Naveen Kumar
 *
 */
@Transactional
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

	public List<Book> findAll();
	
	public List<Book> findByAuthors_AuthorNameAndYear(String authorName, int year);

	public List<Book> findByAuthors_AuthorName(String authorName);

	public List<Book> findByYear(int year);
	
	public List<Book> findByGenre(String genre);

	public List<Book> findByAuthors_AuthorNameAndYearAndGenre(String authorName, int year, String genre);

	public List<Book> findByAuthors_AuthorNameAndGenre(String authorName, String genre);

}
