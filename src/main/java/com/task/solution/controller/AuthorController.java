package com.task.solution.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.task.solution.dto.AuthorDto;
import com.task.solution.service.AuthorService;

/**
 * Controller class to handle author related end points.
 * 
 * @author naveen
 *
 */

@RestController("/authors")
public class AuthorController {

	private AuthorService authorService;

	@PostMapping
	public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
		AuthorDto authorDto2 = this.authorService.createAuthor(authorDto);
		return new ResponseEntity<>(authorDto2, HttpStatus.OK);
	}

}
