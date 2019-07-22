package com.task.solution.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.solution.dto.BookDto;
import com.task.solution.dto.ComicDto;
import com.task.solution.dto.MagazineDto;
import com.task.solution.enums.PublicationTypeEnum;
import com.task.solution.exception.PublicationTypeDoesNotSupportedException;
import com.task.solution.mapper.Converter;
import com.task.solution.model.Author;
import com.task.solution.model.Book;
import com.task.solution.model.Comic;
import com.task.solution.model.Magazine;
import com.task.solution.repository.AuthorRepository;
import com.task.solution.repository.BookRepository;
import com.task.solution.repository.ComicRepository;
import com.task.solution.repository.MagazineRepository;

@Service
public class PublicationServiceImpl implements PublicationService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	ComicRepository comicRepository;

	@Autowired
	MagazineRepository magazineRepository;
	
	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	Converter converter;

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public Object savePublication(String type, JsonNode jsonNode) throws PublicationTypeDoesNotSupportedException {

		if (PublicationTypeEnum.BOOKS.getType().equalsIgnoreCase(type)) {
			BookDto bookDto = this.mapper.convertValue(jsonNode, BookDto.class);
			Book book = (Book) this.converter.convert(bookDto, Book.class);
			Book savedBook = this.bookRepository.save(book);
			return this.converter.convert(savedBook, BookDto.class);
		} else if (PublicationTypeEnum.COMICS.getType().equalsIgnoreCase(type)) {
			ComicDto comicDto = this.mapper.convertValue(jsonNode, ComicDto.class);
			Comic comic = (Comic) this.converter.convert(comicDto, Comic.class);
			Comic savedComic = this.comicRepository.save(comic);
			return this.converter.convert(savedComic, ComicDto.class);
		} else if (PublicationTypeEnum.MAGAZINES.getType().equalsIgnoreCase(type)) {
			MagazineDto magazineDto = this.mapper.convertValue(jsonNode, MagazineDto.class);
			Magazine magazine = (Magazine) this.converter.convert(magazineDto, Magazine.class);
			Magazine savedMagazine = this.magazineRepository.save(magazine);
			return this.converter.convert(savedMagazine, MagazineDto.class);
		}
		throw new PublicationTypeDoesNotSupportedException("Publication type is not supported.");
	}

	@Override
	public List<?> findPublications(String publicationType, String authorName, int year, String genre, String hero,
			String magazineType) throws PublicationTypeDoesNotSupportedException {

		if (PublicationTypeEnum.BOOKS.getType().equalsIgnoreCase(publicationType)) {
			List<Book> books = getBooksBasedOnFilters(authorName, year, genre);
			return books.stream().map(book -> (BookDto) this.converter.convert(book, BookDto.class))
					.collect(Collectors.toList());
		} else if (PublicationTypeEnum.COMICS.getType().equalsIgnoreCase(publicationType)) {
			List<Comic> comics = getComicsBasedOnFilters(authorName, year, hero);
			return comics.stream().map(comic -> (ComicDto) this.converter.convert(comic, ComicDto.class))
					.collect(Collectors.toList());

		} else if (PublicationTypeEnum.MAGAZINES.getType().equalsIgnoreCase(publicationType)) {
			List<Magazine> magazines = getMagazinesBasedOnFilters(authorName, year, magazineType);
			return magazines.stream().map(magazine -> (MagazineDto) this.converter.convert(magazine, MagazineDto.class))
					.collect(Collectors.toList());
		}
		throw new PublicationTypeDoesNotSupportedException("Publication type is not supported.");
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

	private List<Comic> getComicsBasedOnFilters(String authorName, int year, String hero) {
		List<Comic> comicsList = new ArrayList<>();
		if (authorName.isEmpty() && year == 0 && hero.isEmpty()) {
			comicsList = this.comicRepository.findAll();
		} else if (!authorName.isEmpty() && year == 0 && hero.isEmpty()) {
			comicsList = this.comicRepository.findByAuthors_AuthorName(authorName);
		} else if (!authorName.isEmpty() && year != 0 && hero.isEmpty()) {
			comicsList = this.comicRepository.findByAuthors_AuthorNameAndYear(authorName, year);
		} else if (!authorName.isEmpty() && year != 0 && !hero.isEmpty()) {
			comicsList = this.comicRepository.findByAuthors_AuthorNameAndYearAndHero(authorName, year, hero);
		} else if (!authorName.isEmpty() && year == 0 && !hero.isEmpty()) {
			comicsList = this.comicRepository.findByAuthors_AuthorNameAndHero(authorName, hero);
		} else if (authorName.isEmpty() && year != 0 && hero.isEmpty()) {
			comicsList = this.comicRepository.findByYear(year);
		} else if (authorName.isEmpty() && year == 0 && !hero.isEmpty()) {
			comicsList = this.comicRepository.findByHero(hero);
		}
		return comicsList;
	}

	private List<Magazine> getMagazinesBasedOnFilters(String authorName, int year, String magazineType) {
		List<Magazine> magazineList = new ArrayList<>();
		if (authorName.isEmpty() && year == 0 && magazineType.isEmpty()) {
			magazineList = this.magazineRepository.findAll();
		} else if (!authorName.isEmpty() && year == 0 && magazineType.isEmpty()) {
			magazineList = this.magazineRepository.findByAuthors_AuthorName(authorName);
		} else if (!authorName.isEmpty() && year != 0 && magazineType.isEmpty()) {
			magazineList = this.magazineRepository.findByAuthors_AuthorNameAndYear(authorName, year);
		} else if (!authorName.isEmpty() && year != 0 && !magazineType.isEmpty()) {
			magazineList = this.magazineRepository.findByAuthors_AuthorNameAndYearAndMagazineType(authorName, year,
					magazineType);
		} else if (authorName.isEmpty() && year != 0 && magazineType.isEmpty()) {
			magazineList = this.magazineRepository.findByYear(year);
		} else if (authorName.isEmpty() && year == 0 && !magazineType.isEmpty()) {
			magazineList = this.magazineRepository.findByMagazineType(magazineType);
		}
		return magazineList;
	}

}
