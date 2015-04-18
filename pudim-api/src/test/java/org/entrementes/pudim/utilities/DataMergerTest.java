package org.entrementes.pudim.utilities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;

import org.entrementes.pudim.utilities.DataMerger;
import org.junit.Test;

public class DataMergerTest {

	private class Assistente implements Serializable{

		private static final long serialVersionUID = 1L;

		private Calendar objeto;
		
		private List<String> estrutura;
		
		private int primitivo;
		
		private Long empacotado;

		public Assistente(Calendar objeto,
							List<String> estrutura,
							int primitivo,
							Long empacotado) {
			this.empacotado = empacotado;
			this.estrutura = estrutura;
			this.objeto = objeto;
			this.primitivo = primitivo;
		}
		
		public Calendar getObjeto() {
			return objeto;
		}

		public List<String> getEstrutura() {
			return estrutura;
		}
		
		public int getPrimitivo() {
			return primitivo;
		}

		public Long getEmpacotado() {
			return empacotado;
		}
		
	}
	
	private DataMerger<Assistente> cobaia = new DataMerger<DataMergerTest.Assistente>();
	
	@Test
	public void must_not_override_existing_fields() throws Exception {
		Calendar tempo = Calendar.getInstance();
		List<String> estrutura = Arrays.asList("A","B");
		Assistente completo1 = new Assistente(tempo, 
												estrutura, 
												2, 
												7655L);
		Assistente completo2 = new Assistente(Calendar.getInstance(), 
												Arrays.asList("B"), 
												6, 
												9999L);
		Assistente resultado = cobaia.mergeMissing(completo1, completo2);
		Assert.assertEquals(2,resultado.getPrimitivo());
		Assert.assertEquals(new Long(7655L),resultado.getEmpacotado());
		Assert.assertEquals(tempo,resultado.getObjeto());
		Assert.assertEquals(estrutura,resultado.getEstrutura());
	}
	
	@Test
	public void must_fill_blank_fields() throws Exception {
		Calendar tempo = Calendar.getInstance();
		List<String> estrutura = Arrays.asList("A","B");
		Assistente completo1 = new Assistente(null,null,0,null);
		Assistente completo2 = new Assistente(tempo, 
												estrutura, 
												2, 
												7655L);
		Assistente resultado = cobaia.mergeMissing(completo1, completo2);
		Assert.assertEquals(0,resultado.getPrimitivo());//Primitivo nunca é nulo!
		Assert.assertEquals(new Long(7655L),resultado.getEmpacotado());
		Assert.assertEquals(tempo,resultado.getObjeto());
		Assert.assertEquals(estrutura,resultado.getEstrutura());
	}
	
	@Test
	public void must_override_existing_fields() throws Exception {
		Calendar tempo = Calendar.getInstance();
		List<String> estrutura = Arrays.asList("A","B");
		Assistente completo1 = new Assistente(Calendar.getInstance(), 
												Arrays.asList("B"), 
												2, 
												7655L);
		Assistente completo2 = new Assistente(tempo, 
												estrutura, 
												6, 
												9999L);
		Assistente resultado = cobaia.mergeProvidedFields(completo1, completo2);
		Assert.assertEquals(6,resultado.getPrimitivo());
		Assert.assertEquals(new Long(9999L),resultado.getEmpacotado());
		Assert.assertEquals(tempo,resultado.getObjeto());
		Assert.assertEquals(estrutura,resultado.getEstrutura());
	}
	
	@Test
	public void must_override_provided_fields() throws Exception {
		Calendar tempo = Calendar.getInstance();
		List<String> estrutura = Arrays.asList("A","B");
		Assistente completo1 = new Assistente(Calendar.getInstance(), 
												Arrays.asList("B"), 
												2, 
												7655L);
		Assistente completo2 = new Assistente(tempo, 
												estrutura, 
												6, 
												null);
		Assistente resultado = cobaia.mergeProvidedFields(completo1, completo2);
		Assert.assertEquals(6,resultado.getPrimitivo());
		Assert.assertEquals(new Long(7655L),resultado.getEmpacotado());
		Assert.assertEquals(tempo,resultado.getObjeto());
		Assert.assertEquals(estrutura,resultado.getEstrutura());
	}

}
