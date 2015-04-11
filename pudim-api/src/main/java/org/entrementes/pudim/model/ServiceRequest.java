package org.entrementes.pudim.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceRequest")
public class ServiceRequest<T, K extends Serializable> {
	
	private T body;
	
	private K key;
	
	private String messageCode;

	public ServiceRequest() {
	}
	
	public ServiceRequest(K key, String messageCode) {
		this.key = key;
		this.messageCode = messageCode;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

}
