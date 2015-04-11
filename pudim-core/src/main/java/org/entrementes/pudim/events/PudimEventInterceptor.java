package org.entrementes.pudim.events;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.entrementes.pudim.model.builder.LogEntryBuilder;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class PudimEventInterceptor {

	private EventDispatcher dispatcher;

	@PostConstruct
	private void registerHandlers(){
		this.dispatcher.forward(new LogEntryBuilder().level("info")
				.message("MercatorInterceptor: registred.")
				.build());
	}
	
	@Autowired
	public PudimEventInterceptor(EventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Pointcut(value = "execution(@com.mobicare.wifi.util.pudim.events.EventHandler * *(..))")
    public void anyMethod() {
    }

    @Around(value = "anyMethod() && @annotation(handler)", argNames = "handler")
    public Object invoke(final ProceedingJoinPoint callback, final EventHandler eventMetadata) throws Throwable {
    	
    	Object result = callback.proceed();
    	
    	if(eventMetadata != null){
    		this.dispatcher.forward(result);
    	}else{
    		this.dispatcher.forward(new LogEntryBuilder().level("warn").message("no handler for - " + result.getClass()));
    	}
    	
		return result;
	}
}