package org.entrementes.pudim.events;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import org.entrementes.pudim.events.EventDispatcher;
import org.entrementes.pudim.events.EventInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

public class EventDispatcherTest {

	private Class<?> classe;
	
	private Boolean ex;
	
	public class Handler1 implements EventInterceptor<String>{
		@Override
		public void handleEvent(String eventPayload) {
			classe = String.class;
			ex = false;
		}
		@Override
		public void handleEvent(String eventPayload, Exception exception) {
			classe = String.class;
			ex = true;	
		}
	}
	
	public class Handler2 implements EventInterceptor<Integer>{
		@Override
		public void handleEvent(Integer eventPayload) {
			classe = Integer.class;
			ex = false;
		}

		@Override
		public void handleEvent(Integer eventPayload, Exception exception) {
			classe = Integer.class;
			ex = true;
		}
	}
	
	
	private EventDispatcher cobaia;
	
	@Before
	@SuppressWarnings("rawtypes")
	public void buildTestRig(){
		this.classe = null;
		this.ex = null;
		Map<String,EventInterceptor> assistMap = new HashMap<String,EventInterceptor>();
		Handler1 h1 = new Handler1();
		Handler2 h2 = new Handler2();
		assistMap.put("Handler1", h1);
		assistMap.put("Handler2", h2);
		ApplicationContext testContext = Mockito.mock(ApplicationContext.class);
		Mockito.when(testContext.getBeansOfType(EventInterceptor.class)).thenReturn(assistMap);
		this.cobaia = new EventDispatcher(testContext);
	}
	
	@Test
	public void must_forward_payload_to_handler() {
		cobaia.forward("texto!");
		Assert.assertFalse(this.ex);
		Assert.assertEquals(String.class,this.classe);
	}
	
	@Test
	public void must_forward_payload_and_exception_to_handler() {
		cobaia.forward(new Integer(3), new RuntimeException());
		Assert.assertTrue(this.ex);
		Assert.assertEquals(Integer.class,this.classe);
	}
	
	@Test
	public void must_ignore_payload_without_handler() {
		cobaia.forward(Calendar.getInstance());
		Assert.assertNull(this.ex);
		Assert.assertNull(this.classe);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void must_build_empty_dispatcher_test() {
		ApplicationContext emptyContext = Mockito.mock(ApplicationContext.class);
		Mockito.when(emptyContext.getBeansOfType(EventInterceptor.class)).thenReturn(new HashMap<String,EventInterceptor>());
		cobaia = new EventDispatcher(emptyContext);
		cobaia.forward("handle ME!");
		Assert.assertNull(this.ex);
		Assert.assertNull(this.classe);
	}

}
