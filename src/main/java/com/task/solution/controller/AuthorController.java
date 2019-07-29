package com.task.solution.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.solution.dto.AuthorDto;
import com.task.solution.exception.EntityDoesNotExistException;
import com.task.solution.service.AuthorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller class to manage the author resource CRUD operation.
 * 
 * @author Naveen Kumar
 *
 */
@Api(value = "Authors", description = "Controller methods for managing author related operations.")
@RestController
@RequestMapping("/authors")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	/**
	 * Create a author resource.
	 * 
	 * @param authorDto
	 * @return
	 */

	@ApiOperation(value = "Create author record", response = AuthorDto.class)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
		AuthorDto authorDto2 = this.authorService.createAuthor(authorDto);
		return new ResponseEntity<>(authorDto2, HttpStatus.OK);
	}
	
	/** Get List of Authors.
	 * @return list of authors.
	 */
	@ApiOperation(value="Get the list of authors containg publication entities.", response=Iterable.class)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AuthorDto>> getAuthors() {
		List<AuthorDto> authorsList = this.authorService.getAuthors();
		return new ResponseEntity<List<AuthorDto>>(authorsList, HttpStatus.OK);
	}
	
	/** Get single record of author for specified author Id.
	 * @param authorId
	 * @return
	 * @throws EntityDoesNotExistException
	 */
	@ApiOperation(value="Get the author record for the specified author id", response=AuthorDto.class)
	@GetMapping(value = "/{authorId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthorDto> getAuthorsById(@PathVariable int authorId) throws EntityDoesNotExistException {
		try {
			AuthorDto authorDto = this.authorService.getAuthorById(authorId);
			return new ResponseEntity<AuthorDto>(authorDto, HttpStatus.OK);
		} catch (EntityDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/** Update the Author detail for specified Author Id.
	 * @param authorId
	 * @param authorDto
	 * @return updated author record.
	 */
	@ApiOperation(value="Update the author record for the specified author id.", response=AuthorDto.class)
	@PatchMapping(value = "/{authorId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthorDto> updatedAuthorDetails(@PathVariable Integer authorId,
			@RequestBody AuthorDto authorDto) {
		AuthorDto savedAuthorDto = null;
		try {
			savedAuthorDto = this.authorService.updateAuthorDetails(authorId, authorDto);
			return new ResponseEntity<AuthorDto>(savedAuthorDto, HttpStatus.OK);
		} catch (EntityDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/** Delete the author record for the specified author Id.
	 * @param authorId
	 * @return No Content if author is successfully deleted.
	 */
	@ApiOperation(value="Delete the author record for the specified author id", response=Void.class)
	@ApiResponses(value =  {
			@ApiResponse(code = 204, message ="Succesfully deleted the author id"),
			@ApiResponse(code = 404, message ="Author record does not found for the specified author id.")
	})
	@DeleteMapping("/{authorId}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable int authorId) {
		try {
			this.authorService.deleteAuthor(authorId);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (EntityDoesNotExistException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

}
