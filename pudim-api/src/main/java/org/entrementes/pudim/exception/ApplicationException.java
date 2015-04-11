package org.entrementes.pudim.exception;

import org.entrementes.pudim.model.ServiceRequest;

@SuppressWarnings("rawtypes")
public class ApplicationException extends RuntimeException{

	private static final long serialVersionUID = -2471676251759124431L;
	
	private final ExceptionCode code;
	
	private final ServiceRequest request;
	
	public ApplicationException() {
		this.code = ExceptionCode.UNMAPPED;
		this.request = new ServiceRequest<String, String>(this.code.toString(), "embarassing.error.occured");
	}
	
	public ApplicationException(Exception ex) {
		super(ex);
		this.code = ExceptionCode.WRAPPED;
		this.request = new ServiceRequest<String, String>(this.code.toString(), "embarassing.error.occured");
	}
	
	public ApplicationException(ExceptionCode code, Exception ex) {
		super(ex);
		this.code = code;
		this.request = new ServiceRequest<String, String>(this.code.toString(), "embarassing.error.occured");
	}
	
	public ApplicationException(String messageCode, ExceptionCode code, Exception ex) {
		super(messageCode,ex);
		this.code = code;
		this.request = new ServiceRequest<String,String>(this.code.toString(), messageCode);
	}
	
	public ApplicationException(ServiceRequest request, String messageCode, ExceptionCode code, Exception ex) {
		super(messageCode,ex);
		this.code = code;
		this.request = request;
		this.request.setMessageCode(messageCode);
	}
	
	public ApplicationException(ServiceRequest request, String messageCode, ExceptionCode code) {
		super(messageCode);
		this.code = code;
		this.request = request;
		this.request.setMessageCode(messageCode);
	}
	
	public ServiceRequest getRequest() {
		return request;
	}
	
	public ExceptionCode getCode() {
		return code;
	}

}
