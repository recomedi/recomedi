package com.recomedi.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.recomedi.myapp.domain.ScrapDto;

public interface ScrapMapper {

	public ArrayList<ScrapDto> scrapSelectAll(HashMap<String,Object> hm);
	
	public int scrapTotalCount(HashMap<String,Object> hm);
	
	public int scrapDelete(HashMap<String,Object> hm);
		
}
