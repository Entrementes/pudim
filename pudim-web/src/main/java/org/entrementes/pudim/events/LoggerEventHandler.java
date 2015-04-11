package org.entrementes.pudim.events;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.entrementes.pudim.events.EventInterceptor;
import org.entrementes.pudim.model.LogEntry;
import org.springframework.stereotype.Component;

@Component
public class LoggerEventHandler implements EventInterceptor<LogEntry>{

	private final Logger LOGGER = Logger.getLogger(LoggerEventHandler.class);

	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void handleEvent(LogEntry event) {
		String level = event.getLevel() != null ? event.getLevel().toLowerCase() : "";
		switch(level){
		case "debug":
			LOGGER.debug(formatOutput(event));
			break;
		case "info":
			LOGGER.info(formatOutput(event));
			break;
		case "warn":
			LOGGER.warn(formatOutput(event));
			break;
		case "error":
			LOGGER.error(formatOutput(event));
			break;
		default:
			LOGGER.warn("[LOG LEVEL NOT REGISTRED] " + formatOutput(event));
		}
	}

	private String formatOutput(LogEntry event) {
		try{
			return mapper.writeValueAsString(event);
		}catch(Exception ex){
			return "error.formating.json";
		}
	}

	@Override
	public void handleEvent(LogEntry event, Exception exception) {
		LOGGER.error(formatOutput(event), exception);
	}
}

