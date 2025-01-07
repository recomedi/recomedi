package com.recomedi.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recomedi.myapp.domain.ScrapDto;
import com.recomedi.myapp.domain.SearchCriteria;
import com.recomedi.myapp.persistance.ScrapMapper;

@Service
public class ScrapServiceImpl implements ScrapService {

	private ScrapMapper sm;
	
	@Autowired
	public ScrapServiceImpl(SqlSession sqlSession) {
		this.sm = sqlSession.getMapper(ScrapMapper.class);
	}

	@Override
	public ArrayList<ScrapDto> scrapSelectAll(SearchCriteria scri, int midx) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();  // HashMap은 ArrayList와 비슷하지만 "이름: 값"의 형식을 가지고 있다. mybatis에서 권장함
		hm.put("startPageNum", (scri.getPage() - 1) * scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		
		hm.put("midx", midx);
		
		ArrayList<ScrapDto> slist = sm.scrapSelectAll(hm);
		
		return slist;
	}

	@Override
	public int scrapTotalCount(SearchCriteria scri, int midx) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		hm.put("midx", midx);
		
		int cnt = sm.scrapTotalCount(hm);
		
		return cnt;
	}	

	@Override
	public int scrapDelete(int[] sidx, int midx) {
		
		int result = 0;
		
		for(int i = 0; i < sidx.length; i++) {
			HashMap<String,Object> hm = new HashMap<String,Object>();
			hm.put("sidx", sidx[i]);
			hm.put("midx", midx);
			result += sm.scrapDelete(hm);
		}
		
		return result;
	};
	
}
