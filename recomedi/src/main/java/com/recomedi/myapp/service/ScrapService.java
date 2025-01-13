package com.recomedi.myapp.service;

import java.util.ArrayList;

import com.recomedi.myapp.domain.ScrapVo;
import com.recomedi.myapp.domain.SearchCriteria;

public interface ScrapService {	

	public ArrayList<ScrapVo> scrapSelectAll(SearchCriteria scri, int midx);
	
	public int scrapTotalCount(SearchCriteria scri, int midx);
	
	public int scrapDelete(int[] sidx);

	public Integer findSidx(String itemSeq, int midx, String delyn);

	public Integer scrapInsert(ScrapVo sv);

	public Integer scrapUpdate(ScrapVo sv);	
	
}
