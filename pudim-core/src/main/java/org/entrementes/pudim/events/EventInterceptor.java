package org.entrementes.pudim.events;

public interface EventInterceptor<T> {

	void handleEvent(T eventPayload);

	void handleEvent(T eventPayload, Exception exception);

}
