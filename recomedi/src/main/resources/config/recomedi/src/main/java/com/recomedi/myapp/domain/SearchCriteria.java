package com.recomedi.myapp.domain;

public class SearchCriteria extends Criteria {
	
	private String searchType;
	private String title;
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
