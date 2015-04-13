package org.entrementes.pudim.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.entrementes.pudim.events.EventDispatcher;
import org.entrementes.pudim.exception.ApplicationException;
import org.entrementes.pudim.exception.ExceptionCode;
import org.entrementes.pudim.model.ServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HttpExceptionHandler {
	
	private EventDispatcher dispatcher;

	@Autowired
	public HttpExceptionHandler(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	protected ResponseEntity<ServiceRequest<String, String>> handleException(final HttpServletRequest request, final Exception exception) {
		ServiceRequest<String, String> responseMessage = new ServiceRequest<String, String>();
		responseMessage.setBody("unable to process.");
		responseMessage.setKey(request.getRequestURI());
		ResponseEntity<ServiceRequest<String,String>> result;
		if(exception instanceof com.fasterxml.jackson.core.JsonParseException){
			result = new ResponseEntity<ServiceRequest<String, String>>(responseMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}else{
			result = new ResponseEntity<ServiceRequest<String, String>>(responseMessage,HttpStatus.BAD_REQUEST);
		}
		this.dispatcher.forward(result, exception);
		
		return result;
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(value = ApplicationException.class)
	@ResponseBody
	protected ResponseEntity<ServiceRequest> handleException(final HttpServletRequest request, final ApplicationException exception, Locale locale) {
		ResponseEntity<ServiceRequest> result = buildMessage(exception,locale);
		this.dispatcher.forward(result);
		return result;
	}

	@SuppressWarnings("rawtypes")
	private ResponseEntity<ServiceRequest> buildMessage(ApplicationException exception, Locale locale) {
		ServiceRequest originalRequest = exception.getRequest();
		HttpStatus status = translateCode(exception.getCode());
		return new ResponseEntity<ServiceRequest>(originalRequest,status);
	}

	private HttpStatus translateCode(ExceptionCode code) {
		switch(code){
		case UNAUTHORIZED:
			return HttpStatus.UNAUTHORIZED;
		case NOT_FOUND:
			return HttpStatus.NOT_FOUND;
		case CONFLICT:
			return HttpStatus.CONFLICT;
		case BAD_KEY:
		case BAD_REQUEST_BODY:
			return HttpStatus.BAD_REQUEST;
		case CONFIGURATION_ERROR:
			return HttpStatus.SERVICE_UNAVAILABLE;
		default:
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
	
}
