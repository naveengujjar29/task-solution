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
import com.task.solution.dto.ComicDto;
import com.task.solution.exception.EntityDoesNotExistException;
import com.task.solution.exception.PublicationTypeDoesNotSupportedException;
import com.task.solution.mapper.Converter;
import com.task.solution.model.Author;
import com.task.solution.model.Book;
import com.task.solution.model.Comic;
import com.task.solution.repository.AuthorRepository;
import com.task.solution.repository.ComicRepository;

@Service(value = "comics")
@Transactional
public class ComicsServiceImpl implements PublicationService {

	@Autowired
	ComicRepository comicRepository;

	@Autowired
	Converter converter;

	@Autowired
	AuthorRepository authorRepository;

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public ComicDto savePublication(JsonNode jsonNode) throws PublicationTypeDoesNotSupportedException {
		Author author = null;
		ComicDto comicDto = this.mapper.convertValue(jsonNode, ComicDto.class);
		Comic comic = (Comic) this.converter.convert(comicDto, Comic.class);
		for (AuthorDto temp : comicDto.getAuthors()) {
			if (temp.getAuthorId() != 0) {
				author = this.authorRepository.findById(temp.getAuthorId()).get();
				comic.getAuthors().add(author);
				author.getComics().add(comic);
			}
		}
		Comic savedComic = this.comicRepository.save(comic);
		return (ComicDto) this.converter.convert(savedComic, ComicDto.class);
	}

	@Override
	public List<?> findPublications(String authorName, int year, String genre, String hero, String magazineType)
			throws PublicationTypeDoesNotSupportedException {
		List<Comic> comics = getComicsBasedOnFilters(authorName, year, hero);
		return comics.stream().map(comic -> (ComicDto) this.converter.convert(comic, ComicDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<JsonNode> findPublicationsById(int id) {
		JsonNode returnedObject = null;
		Optional<Comic> comic = this.comicRepository.findById(id);
		if (comic.isPresent()) {
			ComicDto comicDto = (ComicDto) this.converter.convert(comic.get(), ComicDto.class);
			returnedObject = this.mapper.convertValue(comicDto, JsonNode.class);
			return Optional.of(returnedObject);
		}
		return Optional.empty();
	}

	@Override
	public Object updatePublication(int id, JsonNode jsonNode) {
		Optional<Comic> comic = this.comicRepository.findById(id);
		if (comic.isPresent()) {
			return patchComicValues(jsonNode, comic);
		} else {
			return Optional.empty();
		}

	}

	private Object patchComicValues(JsonNode jsonNode, Optional<Comic> comic) {
		Comic updatedComicValues = this.mapper.convertValue(jsonNode, Comic.class);
		Comic existingSavedComic = comic.get();
		if (!Objects.isNull(updatedComicValues.getTitle())) {
			existingSavedComic.setTitle(updatedComicValues.getTitle());
		}
		if (!Objects.isNull(updatedComicValues.getHero())) {
			existingSavedComic.setHero(updatedComicValues.getHero());
		}
		if (!Objects.isNull(updatedComicValues.getYear())) {
			existingSavedComic.setYear(updatedComicValues.getYear());
		}
		if (!Objects.isNull(updatedComicValues.getAuthors())) {
			existingSavedComic.setAuthors(updatedComicValues.getAuthors());
		}
		Comic savedObject = this.comicRepository.save(existingSavedComic);
		return savedObject;
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

	@Override
	public void deletePublication(int id) throws EntityDoesNotExistException {
		Optional<Comic> comic = this.comicRepository.findById(id);
		Comic savedComic;
		if (comic.isPresent()) {
			savedComic = comic.get();
			for (Author author : savedComic.getAuthors()) {
				savedComic.getAuthors().remove(author);
				author.getComics().remove(savedComic);
			}
			this.comicRepository.delete(savedComic);
		} else {
			throw new EntityDoesNotExistException("Comic ID does not exist");
		}

	}

}
