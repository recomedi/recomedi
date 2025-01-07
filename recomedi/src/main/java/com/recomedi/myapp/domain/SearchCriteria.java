package com.recomedi.myapp.domain;

public class SearchCriteria extends Criteria {
	
	private String searchType;
	private String keyword;
	private String hashTag;
	
	public String getSearchType() {
		return searchType;
	}
	public String getHashTag() {
		return hashTag;
	}
	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
