package com.task.solution.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectConverter implements Converter {

	ModelMapper mapper = new ModelMapper();

	@Override
	public Object convert(Object object, Class clazz) {
		this.mapper.getConfiguration().setAmbiguityIgnored(true);
		return this.mapper.map(object, clazz);
	}

}
