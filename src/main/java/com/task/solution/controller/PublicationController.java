package com.task.solution.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.solution.dto.BookDto;
import com.task.solution.enums.PublicationTypeEnum;

@RestController("/publications")
public class PublicationController {
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	
	@PostMapping("/{type}")
	public ResponseEntity<JsonNode> createPublication(@PathVariable PublicationTypeEnum type, @RequestBody JsonNode request) {
		
		if(PublicationTypeEnum.MAGAZINE == type) {
			BookDto book = this.objectMapper.convertValue(request, BookDto.class);
			
		}
		
		return null;
	}

}
