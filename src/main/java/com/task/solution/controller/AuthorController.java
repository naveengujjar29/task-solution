package com.task.solution.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.solution.dto.AuthorDto;
import com.task.solution.exception.EntityDoesNotExistException;
import com.task.solution.service.AuthorService;

/**
 * Controller class to handle author related end points.
 * 
 * @author naveen
 *
 */

@RestController
@RequestMapping("/authors")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
		AuthorDto authorDto2 = this.authorService.createAuthor(authorDto);
		return new ResponseEntity<>(authorDto2, HttpStatus.OK);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AuthorDto>> getAuthors() {
		List<AuthorDto> authorsList = this.authorService.getAuthors();
		return new ResponseEntity<List<AuthorDto>>(authorsList, HttpStatus.OK);
	}

	@GetMapping(value = "/{authorId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthorDto> getAuthorsById(@PathVariable int authorId) throws EntityDoesNotExistException {
		try {
			AuthorDto authorDto = this.authorService.getAuthorById(authorId);
			return new ResponseEntity<AuthorDto>(authorDto, HttpStatus.OK);
		} catch (EntityDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{authorId}")
	public ResponseEntity<?> deleteAuthor(@PathVariable int authorId) {
		try {
			this.authorService.deleteAuthor(authorId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (EntityDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

}
