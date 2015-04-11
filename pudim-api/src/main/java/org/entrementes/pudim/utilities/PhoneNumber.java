package org.entrementes.pudim.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.entrementes.pudim.exception.ExceptionCode;
import org.entrementes.pudim.exception.builder.ExceptionBuilder;

public class PhoneNumber {
	
	private static final Pattern PHONE_REGEX = Pattern.compile("(\\s+)?([(])?(\\s+)?(\\d{2})(\\s+)?([)])?(\\s+)?(\\d{4,5})(\\s+)?([-])?(\\s+)?(\\d{4})(\\s+)?");
	
	private final String rawPhone;
	
	private Integer areaCode;
	
	private Integer terminal;
	
	public PhoneNumber(String rawPhone) {
		this.rawPhone = rawPhone;
		Matcher matcher = PHONE_REGEX.matcher(this.rawPhone);
		if(!matcher.find()){
			throw new ExceptionBuilder().bodyType(String.class)
												.key(rawPhone)
												.message("invalid.phone.number")
												.code(ExceptionCode.BAD_REQUEST_BODY)
												.build();
		}
		if(this.rawPhone.length() < 10){
			throw new ExceptionBuilder().bodyType(String.class)
												.key(rawPhone)
												.message("invalid.phone.number")
												.code(ExceptionCode.BAD_REQUEST_BODY)
												.build();
		}
		try{
			this.areaCode = Integer.parseInt(matcher.group(4));
			this.terminal = Integer.parseInt(matcher.group(8) + matcher.group(12));
		}catch(NumberFormatException ex){
			throw new ExceptionBuilder().bodyType(String.class)
												.key(rawPhone)
												.message("invalid.phone.number")
												.code(ExceptionCode.BAD_REQUEST_BODY)
												.build();
		}
	}

	public int getAreaCode() {
		return this.areaCode;
	}

	public int getTerminal() {
		return this.terminal;
	}

	public String getMsisdn() {
		return String.valueOf(this.areaCode) + String.valueOf(this.terminal);
	}

}
