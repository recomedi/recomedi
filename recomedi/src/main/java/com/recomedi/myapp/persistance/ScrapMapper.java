package com.recomedi.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.recomedi.myapp.domain.ScrapVo;

public interface ScrapMapper {

	public ArrayList<ScrapVo> scrapSelectAll(HashMap<String,Object> hm);
	
	public int scrapTotalCount(HashMap<String,Object> hm);
	
	public int scrapDelete(int sidx);

	public Integer findSidx(HashMap<String,Object> hm);
	
	public int scrapInsert(ScrapVo sv);

	public int scrapUpdate(ScrapVo sv);

}
