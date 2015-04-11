package org.entrementes.pudim.web;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.entrementes.pudim.events.EventInterceptor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@SuppressWarnings("rawtypes")
@Component
public class HttpResponseEvent implements EventInterceptor<ResponseEntity>{
	
	private final Logger LOGGER = Logger.getLogger(HttpResponseEvent.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void handleEvent(ResponseEntity eventPayload) {
		String body;
		try {
			body = mapper.writeValueAsString(eventPayload.getBody());
		} catch (Exception ex) {
			body = "error.formating.json";
			LOGGER.error("ERROR:",ex);
		}
		LOGGER.info(eventPayload.getStatusCode() + " - " + body);
	}

	@Override
	public void handleEvent(ResponseEntity eventPayload, Exception exception) {
		handleEvent(eventPayload);
		LOGGER.error("ERROR in request:",exception);
	}

}
