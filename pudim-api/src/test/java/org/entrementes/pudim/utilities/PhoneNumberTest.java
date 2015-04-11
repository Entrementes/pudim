package org.entrementes.pudim.utilities;


import org.entrementes.pudim.exception.ApplicationException;
import org.entrementes.pudim.utilities.PhoneNumber;
import org.junit.Assert;
import org.junit.Test;

public class PhoneNumberTest {
	
	@Test
	public void must_convert_number() {
		PhoneNumber cobaia = new PhoneNumber(" ( 11 ) 5555 - 5577 ");
		Assert.assertEquals(11,cobaia.getAreaCode());
		Assert.assertEquals(55555577,cobaia.getTerminal());
		Assert.assertEquals("1155555577",cobaia.getMsisdn());
		
		cobaia = new PhoneNumber(" 1256555577 ");
		Assert.assertEquals(12,cobaia.getAreaCode());
		Assert.assertEquals(56555577,cobaia.getTerminal());
		Assert.assertEquals("1256555577",cobaia.getMsisdn());
	}
	
	@Test(expected=ApplicationException.class)
	public void must_except_number_in_invalid_format_A() {
		new PhoneNumber(" 5555 - 5577 ");
	}
	
	@Test(expected=ApplicationException.class)
	public void must_except_number_in_invalid_format_B() {
		new PhoneNumber(" (11)5555577 ");
	}
	
	@Test(expected=ApplicationException.class)
	public void must_except_number_in_invalid_format_C() {
		new PhoneNumber(" telefone (11)5555577 ");
	}
	
	@Test(expected=ApplicationException.class)
	public void must_except_number_in_invalid_format_D() {
		new PhoneNumber(" onze ciquenta ... ");
	}

}
