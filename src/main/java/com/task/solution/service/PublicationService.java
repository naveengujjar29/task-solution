package com.task.solution.service;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.task.solution.exception.EntityDoesNotExistException;
import com.task.solution.exception.PublicationTypeDoesNotSupportedException;

/**
 * Interface for providing various method signature on publication types.
 * 
 * @author Naveen Kumar
 *
 */
public interface PublicationService {

	public Object savePublication( JsonNode dto) throws PublicationTypeDoesNotSupportedException;

	public List<?> findPublications(String authorName, int year, String genre, String hero,
			String magazineType) throws PublicationTypeDoesNotSupportedException;

	public Optional<JsonNode> findPublicationsById(int id);

	public Object updatePublication(int id, JsonNode jsonNode);
	
	public void deletePublication(int id) throws EntityDoesNotExistException;
}
