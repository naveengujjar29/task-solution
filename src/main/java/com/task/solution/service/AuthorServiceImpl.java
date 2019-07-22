package com.task.solution.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.solution.dto.AuthorDto;
import com.task.solution.exception.EntityDoesNotExistException;
import com.task.solution.mapper.Converter;
import com.task.solution.model.Author;
import com.task.solution.repository.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private Converter converter;

	@Autowired
	private AuthorRepository authorRespository;

	@Override
	public AuthorDto createAuthor(AuthorDto authorDto) {

		Author author = (Author) this.converter.convert(authorDto, Author.class);

		Author savedAuthor = this.authorRespository.save(author);

		return (AuthorDto) this.converter.convert(savedAuthor, AuthorDto.class);
	}

	@Override
	public List<AuthorDto> getAuthors() {

		List<Author> authorList = (List<Author>) this.authorRespository.findAll();

		return authorList.stream().map(author -> (AuthorDto) this.converter.convert(author, AuthorDto.class))
				.collect(Collectors.toList());

	}

	@Override
	public void deleteAuthor(int authorId) throws EntityDoesNotExistException {
		Optional<Author> author = this.authorRespository.findById(authorId);

		if (author.isPresent()) {
			this.authorRespository.delete(author.get());
		} else {
			throw new EntityDoesNotExistException("Author ID does not exist");
		}
	}

	@Override
	public AuthorDto getAuthorById(int authorId) throws EntityDoesNotExistException {
		Optional<Author> author = this.authorRespository.findById(authorId);

		if (author.isPresent()) {
			return (AuthorDto) this.converter.convert(author.get(), AuthorDto.class);
		} else {
			throw new EntityDoesNotExistException("Author ID does not exist");
		}
	}

}
