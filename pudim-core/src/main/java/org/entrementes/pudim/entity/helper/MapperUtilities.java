package org.entrementes.pudim.entity.helper;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;

public class MapperUtilities {
	
	public static <T, U> List<U> map(final Mapper mapper, final List<T> source, final Class<U> destType) {

	    final List<U> dest = new ArrayList<>();

	    for (T element : source) {
	        dest.add(mapper.map(element, destType));
	    }

	    return dest;
	}

}
