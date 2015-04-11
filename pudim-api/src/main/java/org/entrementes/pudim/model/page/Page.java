package org.entrementes.pudim.model.page;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MercatorPage", namespace = "http://www.mobicare.com/wifi/1_0_0")
public class Page<T> {
	
	@XmlElementWrapper(name="body")
	protected T[] body;
	
	protected int pageNumber;
	
	protected int itemCount;
	
	protected int size;
	
	protected Map<String,String> links;
	
	public Page() {
	}
	
	@SuppressWarnings("unchecked")
	public Page(List<T> body, int pageNumber, int itemCount, int size, Map<String,String> links) {
		this.body = (T[]) body.toArray();
		this.pageNumber = pageNumber;
		this.links = links;
		this.itemCount = itemCount;
		this.size = size;
	}
	
	public int getSize(){
		return this.size;
	}

	public T[] getBody() {
		return body;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public Map<String, String> getLinks() {
		return links;
	}
	
	public int getItemCount() {
		return itemCount;
	}

}
