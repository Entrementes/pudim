package org.entrementes.pudim.exception.builder;

import java.io.Serializable;

import org.entrementes.pudim.exception.ApplicationException;
import org.entrementes.pudim.exception.ExceptionCode;
import org.entrementes.pudim.model.ServiceRequest;

public class ExceptionBuilder {
	
	private Serializable key;
	
	private String messageCode;
	
	private ExceptionCode code;

	private Class<?> bodyClass;

	private Exception wrappped;

	private Object body;

	public ExceptionBuilder code(ExceptionCode code){
		this.code = code;
		return this;
	}
	
	public ExceptionBuilder message(String messageCode){
		this.messageCode = messageCode;
		return this;
	}
	
	public ExceptionBuilder bodyType(Class<?> bodyClass) {
		this.bodyClass = bodyClass;
		return this;
	}
	
	public ExceptionBuilder key(Serializable key){
		this.key = key;
		return this;
	}
	
	public ExceptionBuilder exception(Exception wrappped) {
		this.wrappped = wrappped;
		return this;
	}
	
	public ApplicationException build(){
		if(key == null){
			key = "";
		}
		if(wrappped == null){
			return new ApplicationException(buildRequest(this.bodyClass,key.getClass()), this.messageCode, this.code);
		}else{
			return new ApplicationException(buildRequest(this.bodyClass,key.getClass()), this.messageCode, this.code,this.wrappped);
		}
	}

	@SuppressWarnings("unchecked")
	private <T, S extends Serializable> ServiceRequest<T, S> buildRequest(Class<T> body,Class<S> keyClass) {
		ServiceRequest<T, S> request = new ServiceRequest<T, S>((S) key, messageCode);
		if(body != null){
			request.setBody((T)this.body);
		}
		return request;
	}

	public ExceptionBuilder body(Object body) {
		this.body = body;
		return this;
	}

}
