package com.task.solution.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Converter class for mapping DTO to Model class of various POJO class.
 * @author Naveen Kumar
 *
 */
@Component
public class ObjectConverter implements Converter {

	ModelMapper mapper = new ModelMapper();

	@SuppressWarnings("unchecked")
	@Override
	public Object convert(Object object, Class clazz) {
		this.mapper.getConfiguration().setAmbiguityIgnored(true);
		return this.mapper.map(object, clazz);
	}

}
