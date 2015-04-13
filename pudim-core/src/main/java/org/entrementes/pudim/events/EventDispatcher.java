package org.entrementes.pudim.events;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.entrementes.pudim.model.builder.LogEntryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {
	
	@SuppressWarnings("rawtypes")
	private Map<Type,EventInterceptor> handlers;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	public EventDispatcher(ApplicationContext context){
		Map<String,EventInterceptor> registredBeans = context.getBeansOfType(EventInterceptor.class);
		this.handlers = new HashMap<Type, EventInterceptor>();
		for(EventInterceptor handler : registredBeans.values()){
			Type type = handler.getClass().getGenericInterfaces()[0];
			if (type instanceof ParameterizedType) {
			    Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];
			    this.handlers.put(actualType, handler);
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	@PostConstruct
	private void registerHandlers(){
		if(this.handlers == null){
			this.handlers = new HashMap<Type, EventInterceptor>();
		}
		forward(new LogEntryBuilder().level("info")
				.message("EventDispatcher: " + this.handlers.size() + " handlers registred.")
				.build());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void forward(Object eventPayload) {
		EventInterceptor handler = this.handlers.get(eventPayload.getClass());
		if(handler != null){
			handler.handleEvent(eventPayload);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void forward(Object eventPayload, Exception exception) {
		EventInterceptor handler = this.handlers.get(eventPayload.getClass());
		if(handler != null){
			handler.handleEvent(eventPayload,exception);
		}
	}
	
	

}
