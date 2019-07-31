package com.task.solution.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.solution.dto.AuthorDto;
import com.task.solution.dto.BookDto;
import com.task.solution.exception.EntityDoesNotExistException;
import com.task.solution.exception.PublicationTypeDoesNotSupportedException;
import com.task.solution.mapper.Converter;
import com.task.solution.model.Author;
import com.task.solution.model.Book;
import com.task.solution.repository.AuthorRepository;
import com.task.solution.repository.BookRepository;

@Service(value = "books")
@Transactional
public class BooksServiceImpl implements PublicationService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	Converter converter;

	@Autowired
	AuthorRepository authorRepository;

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public BookDto savePublication(JsonNode jsonNode) throws PublicationTypeDoesNotSupportedException {
		Author author = null;
		BookDto bookDto = this.mapper.convertValue(jsonNode, BookDto.class);
		Book book = (Book) this.converter.convert(bookDto, Book.class);
		for (AuthorDto temp : bookDto.getAuthors()) {
			if (temp.getAuthorId() != 0) {
				author = this.authorRepository.findById(temp.getAuthorId()).get();
				book.getAuthors().add(author);
				author.getBooks().add(book);
			}
		}
		Book savedBook = this.bookRepository.save(book);
		return (BookDto) this.converter.convert(savedBook, BookDto.class);
	}

	@Override
	public List<?> findPublications(String authorName, int year, String genre, String hero, String magazineType)
			throws PublicationTypeDoesNotSupportedException {
		List<Book> books = getBooksBasedOnFilters(authorName, year, genre);
		return books.stream().map(book -> (BookDto) this.converter.convert(book, BookDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<JsonNode> findPublicationsById(int id) {
		JsonNode returnedObject = null;
		Optional<Book> book = this.bookRepository.findById(id);
		if (book.isPresent()) {
			BookDto bookDto = (BookDto) this.converter.convert(book.get(), BookDto.class);
			returnedObject = this.mapper.convertValue(bookDto, JsonNode.class);
			return Optional.of(returnedObject);
		}
		return Optional.empty();
	}

	@Override
	public Object updatePublication(int id, JsonNode jsonNode) {
		Optional<Book> book = this.bookRepository.findById(id);
		if (book.isPresent()) {
			return patchBookValues(jsonNode, book);
		} else {
			return Optional.empty();
		}

	}

	private Object patchBookValues(JsonNode jsonNode, Optional<Book> book) {
		Book updatedBook = this.mapper.convertValue(jsonNode, Book.class);
		Book existingSavedBook = book.get();
		if (!Objects.isNull(updatedBook.getTitle())) {
			existingSavedBook.setTitle(updatedBook.getTitle());
		}
		if (!Objects.isNull(updatedBook.getGenre())) {
			existingSavedBook.setGenre(updatedBook.getGenre());
		}
		if (!Objects.isNull(updatedBook.getYear())) {
			existingSavedBook.setYear(updatedBook.getYear());
		}
		if (!Objects.isNull(updatedBook.getAuthors())) {
			for (Author temp : updatedBook.getAuthors()) {
				if (temp.getAuthorId() != 0) {
					Author author = this.authorRepository.findById(temp.getAuthorId()).get();
					existingSavedBook.getAuthors().add(author);
					author.getBooks().add(existingSavedBook);
				}
			}
		}
		Book savedObject = this.bookRepository.saveAndFlush(existingSavedBook);
		return savedObject;
	}

	private List<Book> getBooksBasedOnFilters(String authorName, int year, String genre) {
		List<Book> bookList = new ArrayList<>();
		if (authorName.isEmpty() && year == 0 && genre.isEmpty()) {
			bookList = this.bookRepository.findAll();
		} else if (!authorName.isEmpty() && year == 0 && genre.isEmpty()) {
			bookList = this.bookRepository.findByAuthors_AuthorName(authorName);
		} else if (!authorName.isEmpty() && year != 0 && genre.isEmpty()) {
			bookList = this.bookRepository.findByAuthors_AuthorNameAndYear(authorName, year);
		} else if (!authorName.isEmpty() && year != 0 && !genre.isEmpty()) {
			bookList = this.bookRepository.findByAuthors_AuthorNameAndYearAndGenre(authorName, year, genre);
		} else if (authorName.isEmpty() && year != 0 && genre.isEmpty()) {
			bookList = this.bookRepository.findByYear(year);
		} else if (authorName.isEmpty() && year == 0 && !genre.isEmpty()) {
			bookList = this.bookRepository.findByGenre(genre);
		} else if (!authorName.isEmpty() && year == 0 && !genre.isEmpty()) {
			bookList = this.bookRepository.findByAuthors_AuthorNameAndGenre(authorName, genre);
		}
		return bookList;
	}

	@Override
	public void deletePublication(int id) throws EntityDoesNotExistException {
		Optional<Book> book = this.bookRepository.findById(id);
		Book savedBook;
		if (book.isPresent()) {
			savedBook = book.get();
			for (Author author : savedBook.getAuthors()) {
				savedBook.getAuthors().remove(author);
				author.getBooks().remove(savedBook);
			}
			this.bookRepository.delete(savedBook);
		} else {
			throw new EntityDoesNotExistException("Book ID does not exist");
		}

	}

}
