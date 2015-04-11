package org.entrementes.pudim.model.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.entrementes.pudim.model.PageKey;
import org.entrementes.pudim.model.page.Page;

public class PageBuilder<T> {

	private PageKey pageRequest;

	private List<T> pageBody;

	private int pageCount;

	private int itemCount;

	public PageBuilder( PageKey pageRequest,List<T> pageBody, int pageCount, int itemCount, int size) {
		this.pageRequest = pageRequest;
		this.pageBody = pageBody;
		this.pageCount = pageCount;
		this.itemCount = itemCount;
	}
	
	public PageBuilder() {
	}
	
	
	public PageBuilder<T> pageRequest(PageKey pageRequest){
		this.pageRequest = pageRequest;
		return this;
	}
	
	public PageBuilder<T> body(List<T> pageBody){
		this.pageBody = pageBody;
		return this;
	}
	
	public PageBuilder<T> pageCount(int pageCount){
		this.pageCount = pageCount;
		return this;
	}
	
	public PageBuilder<T> itemCount(int itemCount){
		this.itemCount = itemCount;
		return this;
	}

	public Page<T> build() {
		PageKey key = getKey();
		List<T> bodySubset = pageBody.size() > key.getPositions() ? pageBody.subList(0, key.getPositions()) : pageBody;
		int finalSize = ( this.pageRequest == null ) ? this.pageBody.size() : this.pageRequest.getPositions();
		return new Page<T>(bodySubset, key.getPage(), itemCount, finalSize, buildLinks(pageCount));
	}
	
	private PageKey getKey(){
		return ( this.pageRequest == null ? PageKey.defaultKey(): this.pageRequest );
	}

	public Map<String, String> buildLinks(int pageCount) {
		Map<String, String> result = new HashMap<String, String>();
		if(hasFirst()){
			result.put("first", buildLink(1));//
		}
		if(hasNext()){
			result.put("next", buildLink(getKey().getPage() + 1));
		}
		if(hasLast()){
			result.put("last", buildLink(pageCount));
		}
		if(hasPrevious()){
			result.put("previous", buildLink(getKey().getPage() - 1));
		}
		if(hasNumeric()){
			for(int i = getKey().getPage() - 3; i < getKey().getPage(); i ++ ){
				if( i > 0 ){
					result.put(String.valueOf(i),  buildLink(i));
				}
			}
			for(int i = getKey().getPage() + 1; i <= getKey().getPage() + 3; i ++ ){
				if(i <= pageCount){
					result.put(String.valueOf(i), buildLink(i));
				}
			}
		}
		return result;
	}

	private String buildLink(int position) {
		return getKey().getLinkBase().replace("{pageNumber}",String.valueOf(position));
	}

	private boolean hasNumeric() {
		return this.pageCount > 5;
	}

	private boolean hasPrevious() {
		return getKey().getPage() > 1;
	}

	private boolean hasLast() {
		return getKey().getPage() < this.pageCount -2;
	}

	private boolean hasNext() {
		return getKey().getPage() < this.pageCount -1;
	}

	private boolean hasFirst() {
		return getKey().getPage() > 2;
	}

}
