package com.recomedi.myapp.service;

import java.util.ArrayList;

import com.recomedi.myapp.domain.ScrapDto;
import com.recomedi.myapp.domain.SearchCriteria;

public interface ScrapService {	

	public ArrayList<ScrapDto> scrapSelectAll(SearchCriteria scri, int midx);
	
	public int scrapTotalCount(SearchCriteria scri, int midx);
	
	public int scrapDelete(int[] sidx, int midx);
	
}
