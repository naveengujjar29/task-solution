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
import com.task.solution.dto.MagazineDto;
import com.task.solution.exception.EntityDoesNotExistException;
import com.task.solution.exception.PublicationTypeDoesNotSupportedException;
import com.task.solution.mapper.Converter;
import com.task.solution.model.Author;
import com.task.solution.model.Magazine;
import com.task.solution.repository.AuthorRepository;
import com.task.solution.repository.MagazineRepository;

@Service(value = "magazines")
@Transactional
public class MagazineServiceImpl implements PublicationService {

	@Autowired
	MagazineRepository magazineRepository;

	@Autowired
	Converter converter;

	@Autowired
	AuthorRepository authorRepository;

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public MagazineDto savePublication(JsonNode jsonNode) throws PublicationTypeDoesNotSupportedException {
		Author author = null;
		MagazineDto magazineDto = this.mapper.convertValue(jsonNode, MagazineDto.class);
		Magazine magazine = (Magazine) this.converter.convert(magazineDto, Magazine.class);
		for (AuthorDto temp : magazineDto.getAuthors()) {
			if (temp.getAuthorId() != 0) {
				author = this.authorRepository.findById(temp.getAuthorId()).get();
				magazine.getAuthors().add(author);
				author.getMagazines().add(magazine);
			}
		}
		Magazine savedMagazine = this.magazineRepository.save(magazine);
		return (MagazineDto) this.converter.convert(savedMagazine, MagazineDto.class);
	}

	@Override
	public List<?> findPublications(String authorName, int year, String genre, String hero, String magazineType)
			throws PublicationTypeDoesNotSupportedException {
		List<Magazine> magazines = getMagazinesBasedOnFilters(authorName, year, magazineType);
		return magazines.stream().map(magazine -> (MagazineDto) this.converter.convert(magazine, MagazineDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<JsonNode> findPublicationsById(int id) {
		JsonNode returnedObject = null;
		Optional<Magazine> magazine = this.magazineRepository.findById(id);
		if (magazine.isPresent()) {
			MagazineDto magazineDto = (MagazineDto) this.converter.convert(magazine.get(), MagazineDto.class);
			returnedObject = this.mapper.convertValue(magazineDto, JsonNode.class);
			return Optional.of(returnedObject);
		}
		return Optional.empty();
	}

	@Override
	public Object updatePublication(int id, JsonNode jsonNode) {
		Optional<Magazine> magazine = this.magazineRepository.findById(id);
		if (magazine.isPresent()) {
			return patchMagazineValues(jsonNode, magazine);
		} else {
			return Optional.empty();
		}

	}

	private Object patchMagazineValues(JsonNode jsonNode, Optional<Magazine> comic) {
		Magazine updatedMagazineValues = this.mapper.convertValue(jsonNode, Magazine.class);
		Magazine existingSavedMagazine = comic.get();
		if (!Objects.isNull(updatedMagazineValues.getTitle())) {
			existingSavedMagazine.setTitle(updatedMagazineValues.getTitle());
		}
		if (!Objects.isNull(updatedMagazineValues.getMagazineType())) {
			existingSavedMagazine.setMagazineType(updatedMagazineValues.getMagazineType());
		}
		if (!Objects.isNull(updatedMagazineValues.getYear())) {
			existingSavedMagazine.setYear(updatedMagazineValues.getYear());
		}
		if (!Objects.isNull(updatedMagazineValues.getAuthors())) {
			for (Author temp : updatedMagazineValues.getAuthors()) {
				if (temp.getAuthorId() != 0) {
					Author author = this.authorRepository.findById(temp.getAuthorId()).get();
					updatedMagazineValues.getAuthors().add(author);
					author.getMagazines().add(updatedMagazineValues);
				}
			}
		}
		Magazine savedObject = this.magazineRepository.save(existingSavedMagazine);
		return savedObject;
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

	@Override
	public void deletePublication(int id) throws EntityDoesNotExistException {
		Optional<Magazine> magazine = this.magazineRepository.findById(id);
		Magazine savedMagazine;
		if (magazine.isPresent()) {
			savedMagazine = magazine.get();
			for (Author author : savedMagazine.getAuthors()) {
				savedMagazine.getAuthors().remove(author);
				author.getMagazines().remove(savedMagazine);
			}
			this.magazineRepository.delete(savedMagazine);
		} else {
			throw new EntityDoesNotExistException("Magazine ID does not exist");
		}

	}

}
