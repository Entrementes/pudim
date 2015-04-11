package org.entrementes.pudim.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PageKey")
public class PageKey implements Serializable{

	private static final long serialVersionUID = -7703786253211439375L;

	private static final int INITIAL_PAGE = 0;

	private static final Integer DEFAULT_PAGE_SIZE = 10;
	
	private static final PageKey DEFAULT_KEY = new PageKey(INITIAL_PAGE, DEFAULT_PAGE_SIZE, "");
	
	private int page;
	
	private int positions;
	
	private final String linkBase;

	public PageKey(String page, String positions, String linkBase) {
		try{
			this.page = Integer.parseInt(page);
		}catch(NumberFormatException | NullPointerException ex){
			this.page = INITIAL_PAGE;
		}
		try{
			this.positions = Integer.parseInt(positions);
		}catch(NumberFormatException | NullPointerException ex){
			this.positions = DEFAULT_PAGE_SIZE;
		}
		this.linkBase = linkBase;
	}
	
	public PageKey(Integer page, Integer positions, String linkBase) {
		this.page = ( page == null ? INITIAL_PAGE : page );
		this.positions = ( positions == null ? DEFAULT_PAGE_SIZE : positions );
		this.linkBase = linkBase;
	}

	public int getPage() {
		return page;
	}

	public int getPositions() {
		return positions;
	}

	public String getLinkBase() {
		return linkBase;
	}

	public static PageKey defaultKey() {
		return DEFAULT_KEY;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((linkBase == null) ? 0 : linkBase.hashCode());
		result = prime * result + page;
		result = prime * result + positions;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageKey other = (PageKey) obj;
		if (linkBase == null) {
			if (other.linkBase != null)
				return false;
		} else if (!linkBase.equals(other.linkBase))
			return false;
		if (page != other.page)
			return false;
		if (positions != other.positions)
			return false;
		return true;
	}
	
}
