package org.entrementes.pudim.model.builder;

import java.util.Arrays;

import org.entrementes.pudim.model.PageKey;
import org.entrementes.pudim.model.builder.PageBuilder;
import org.entrementes.pudim.model.page.Page;
import org.junit.Assert;
import org.junit.Test;

public class PageBuilderTest {

	private PageBuilder<String> cobaia;
	
	@Test
	public void must_build_default_page_on_empty_paramaters() {
		this.cobaia = new PageBuilder<String>();
		this.cobaia.body(Arrays.asList("1","2","3","4"));
		this.cobaia.pageCount(1);
		this.cobaia.pageRequest(null);
		Page<String> page = this.cobaia.build();
		Assert.assertEquals(4,page.getSize());
		Assert.assertEquals(0,page.getPageNumber());
		Assert.assertEquals(0,page.getLinks().size());
	}
	
	@Test
	public void must_use_correct_page_size() {
		this.cobaia = new PageBuilder<String>();
		this.cobaia.body(Arrays.asList("1","2","3","4","5","6","7"));
		this.cobaia.pageCount(1);
		PageKey key = new PageKey(1, 2, "/strings/{pageNumber}");
		this.cobaia.pageRequest(key);
		this.cobaia.pageCount(10);
		Page<String> page = this.cobaia.build();
		Assert.assertEquals(2,page.getSize());
		Assert.assertEquals(1,page.getPageNumber());
		//2, 3, 4, next, last, 
		Assert.assertEquals(5,page.getLinks().size());
		Assert.assertEquals("/strings/2",page.getLinks().get("next"));
		Assert.assertEquals("/strings/10",page.getLinks().get("last"));
		Assert.assertEquals("/strings/2",page.getLinks().get("2"));
		Assert.assertEquals("/strings/3",page.getLinks().get("3"));
		Assert.assertEquals("/strings/4",page.getLinks().get("4"));
	}

}
