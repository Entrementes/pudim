package org.entrementes.pudim.utilities;

import java.lang.reflect.Field;

public class DataMerger<T>{

	/**
	 * Merge only missing (null) fields from the @param merged object
	 * 
	 * @param merged
	 * @param toBeMerged
	 * @return
	 * @throws Exception
	 */
	public T mergeMissing(T merged, T toBeMerged) throws Exception {
		Field[] fields = merged.getClass().getDeclaredFields();
		for(Field field : fields){
			try{
				field.setAccessible(true);
				if(field.get(merged) == null){
					field.set(merged, field.get(toBeMerged));
				}
				field.setAccessible(false);
			}catch(Exception ex){
				throw ex;
			}
		}
		return merged;
	}
	
	/**
	 * Merge only existing (not null) fields from the @param toBeMerged object
	 * 
	 * @param merged
	 * @param toBeMerged
	 * @return
	 * @throws Exception
	 */
	public T mergeProvidedFields(T merged, T toBeMerged) throws Exception {
		Field[] fields = merged.getClass().getDeclaredFields();
		for(Field field : fields){
			try{
				field.setAccessible(true);
				if(field.get(toBeMerged) != null && fieldIsValid(field)){
					field.set(merged, field.get(toBeMerged));
				}
				field.setAccessible(false);
			}catch(Exception ex){
				throw ex;
			}
		}
		return merged;
	}

	private boolean fieldIsValid(Field field) {
		return !java.lang.reflect.Modifier.isStatic(field.getModifiers());
	}
}
