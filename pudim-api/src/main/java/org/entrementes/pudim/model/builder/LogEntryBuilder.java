package org.entrementes.pudim.model.builder;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.entrementes.pudim.model.LogEntry;

public class LogEntryBuilder {
	
	private final LogEntry body;
	
	public LogEntryBuilder() {
		this.body = new LogEntry();
	}
	
	public LogEntryBuilder level(String level){
		this.body.setLevel(level);
		return this;
	}
	
	public LogEntryBuilder message(String message){
		this.body.setMessage(message);
		return this;
	}
	
	public LogEntry build(){
		this.body.setTimestamp(getTimestamp());
		return this.body;
	}
	
	private XMLGregorianCalendar getTimestamp() {
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)Calendar.getInstance());
		} catch (DatatypeConfigurationException e) {
			return null;
		}
	}

}
