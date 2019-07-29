package com.task.solution.service;

import java.util.List;

import com.task.solution.dto.AuthorDto;
import com.task.solution.exception.EntityDoesNotExistException;

/**
 * Service class for providing CRUD operation signature for author entity.
 * @author Naveen Kumar
 *
 */
public interface AuthorService {

	public AuthorDto createAuthor(AuthorDto authorDto);

	public AuthorDto getAuthorById(int authorId) throws EntityDoesNotExistException;

	public List<AuthorDto> getAuthors();

	public void deleteAuthor(int authorId) throws EntityDoesNotExistException;

	public AuthorDto updateAuthorDetails(int authorId, AuthorDto authorDto) throws EntityDoesNotExistException;

}
