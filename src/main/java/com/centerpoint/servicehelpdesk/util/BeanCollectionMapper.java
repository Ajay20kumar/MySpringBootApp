package com.centerpoint.servicehelpdesk.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

public class BeanCollectionMapper {
	
	private BeanCollectionMapper() {
		
	}
	
	public static <T, U> List<U> mapList(final ModelMapper mapper, final List<T> source, final Class<U> destType) {
		return source.parallelStream().map(k -> mapper.map(k, destType)).collect(Collectors.toList());
	}
	
}
