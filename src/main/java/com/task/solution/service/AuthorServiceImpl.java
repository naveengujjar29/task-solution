package com.task.solution.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.solution.dto.AuthorDto;
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

}
