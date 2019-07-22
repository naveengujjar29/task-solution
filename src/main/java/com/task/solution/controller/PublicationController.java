
package com.task.solution.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.solution.dto.BookDto;
import com.task.solution.dto.ComicDto;
import com.task.solution.enums.PublicationTypeEnum;
import com.task.solution.exception.PublicationTypeDoesNotSupportedException;
import com.task.solution.service.PublicationService;

@RestController
@RequestMapping("/publications")
public class PublicationController {

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private PublicationService publicationService;

	@PostMapping("/{type}")
	public ResponseEntity<JsonNode> createPublication(@PathVariable String type, @RequestBody JsonNode request) {
		try {
			Object object =  this.publicationService.savePublication(type, request);
			JsonNode returnObject = this.objectMapper.convertValue(object, JsonNode.class);
			return new ResponseEntity<JsonNode>(returnObject, HttpStatus.CREATED);
		} catch (PublicationTypeDoesNotSupportedException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonNode> getPublications(@PathVariable String type,
			@RequestParam(defaultValue = "", required = false) String authorName,
			@RequestParam(defaultValue = "0", required = false) Integer year,
			@RequestParam(defaultValue = "", required = false) String genre,
			@RequestParam(defaultValue = "", required = false) String hero,
			@RequestParam(defaultValue = "", required = false) String magazineType) {

		List<?> list;
		try {
			list = publicationService.findPublications(type, authorName, year, genre, hero, magazineType);
			JsonNode returnObject = this.objectMapper.convertValue(list, JsonNode.class);
			return new ResponseEntity<JsonNode>(returnObject, HttpStatus.OK);
		} catch (PublicationTypeDoesNotSupportedException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

}
