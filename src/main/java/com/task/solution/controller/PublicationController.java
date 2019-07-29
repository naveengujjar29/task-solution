
package com.task.solution.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.solution.dto.BookDto;
import com.task.solution.enums.PublicationTypeEnum;
import com.task.solution.exception.EntityDoesNotExistException;
import com.task.solution.exception.PublicationTypeDoesNotSupportedException;
import com.task.solution.service.PublicationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller class to manage different type of publications record.
 * @author Naveen Kumar
 *
 */
@Api(value="Publication Controller", description="Controller class to manage publications types(books, comics, magazines)  record")
@RestController
@RequestMapping("/publications")
public class PublicationController {

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	Map<String, PublicationService> publicationService;
	
	/** Create the publication record for the specified publication type.
	 * @param publicationType
	 * @param request
	 * @return created publication record.
	 */
	@ApiOperation(value="To create the record for the specified publication type.")
	@ApiParam(name="type", value="Type of publications i.e.  books, comics, magazines")
	@PostMapping("/{publicationType}")
	@ApiResponses({
		@ApiResponse(response=BookDto.class, code = 201, message = "succesfully created the publication record.")
	})
	public ResponseEntity<JsonNode> createPublication(@PathVariable PublicationTypeEnum publicationType, @RequestBody JsonNode request) {
		try {
			Object object = this.publicationService.get(publicationType.getType()).savePublication(request);
			JsonNode returnObject = this.objectMapper.convertValue(object, JsonNode.class);
			return new ResponseEntity<JsonNode>(returnObject, HttpStatus.CREATED);
		} catch (PublicationTypeDoesNotSupportedException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/** Get the list of publication based on specified publication type. User can also provide various filter option
	 * on different parameters for the specified publication type.
	 * @param publicationType
	 * @param authorName
	 * @param year
	 * @param genre
	 * @param hero
	 * @param magazineType
	 * @return list of filtered records.
	 */
	@ApiOperation(value="Fetch the list of records for the specified publication type")
	@GetMapping(value = "/{publicationType}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonNode> getPublications(@PathVariable PublicationTypeEnum publicationType,
			@RequestParam(defaultValue = "", required = false) String authorName,
			@RequestParam(defaultValue = "0", required = false) Integer year,
			@RequestParam(defaultValue = "", required = false) String genre,
			@RequestParam(defaultValue = "", required = false) String hero,
			@RequestParam(defaultValue = "", required = false) String magazineType) {

		List<?> list;
		try {
			list = this.publicationService.get(publicationType.getType()).findPublications(authorName, year, genre, hero, magazineType);
			JsonNode returnObject = this.objectMapper.convertValue(list, JsonNode.class);
			return new ResponseEntity<JsonNode>(returnObject, HttpStatus.OK);
		} catch (PublicationTypeDoesNotSupportedException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	/** Get a single record of specified Id for publication type.
	 * @param publicationType
	 * @param id
	 * @return
	 */
	@ApiOperation(value="Fetch the single publication record for the specified publication type with id.")
	@GetMapping(value = "/{publicationType}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonNode> getPublicationsById(@PathVariable PublicationTypeEnum publicationType, @PathVariable Integer id) {
		Optional<JsonNode> object = this.publicationService.get(publicationType.getType()).findPublicationsById(id);
		if (object.isPresent())
			return new ResponseEntity<JsonNode>(object.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	/** Update the publication record for the specified Id and type.
	 * @param publicationType
	 * @param id
	 * @param jsonNode
	 * @return
	 */
	@ApiOperation("Update the publication record for the specified publication type with id.")
	@PatchMapping(value = "/{publicationType}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonNode> updatedPublicationById(@PathVariable PublicationTypeEnum publicationType, @PathVariable Integer id,
			@RequestBody JsonNode jsonNode) {
		Object object = this.publicationService.get(publicationType.getType()).updatePublication(id, jsonNode);
		JsonNode returnObject = this.objectMapper.convertValue(object, JsonNode.class);
		return new ResponseEntity<JsonNode>(returnObject, HttpStatus.OK);
	}
	
	/** Delete the publication record.
	 * @param publicationType
	 * @param id
	 * @param jsonNode
	 * @return
	 */
	@ApiOperation("Delete the publication record for the specified publication type with id.")
	@DeleteMapping(value = "/{publicationType}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deletePublicationById(@PathVariable PublicationTypeEnum publicationType, @PathVariable Integer id,
			@RequestBody JsonNode jsonNode) {
		try {
			this.publicationService.get(publicationType.getType()).deletePublication(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (EntityDoesNotExistException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

	}
}
