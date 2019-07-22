package com.task.solution.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.task.solution.exception.PublicationTypeDoesNotSupportedException;

public interface PublicationService {

	public Object savePublication(String type, JsonNode dto) throws PublicationTypeDoesNotSupportedException;

	public List<?> findPublications(String publicationType, String authorName, int year, String genre, String hero,
			String magazineType) throws PublicationTypeDoesNotSupportedException;

}
