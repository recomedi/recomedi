package com.recomedi.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recomedi.myapp.domain.BoardVo;
import com.recomedi.myapp.domain.SearchCriteria;
import com.recomedi.myapp.persistance.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper bm;
	
	@Autowired
	public BoardServiceImpl(SqlSession sqlSession) {
		this.bm = sqlSession.getMapper(BoardMapper.class);
	}
	
	@Override
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();		
		hm.put("startPageNum", (scri.getPage()-1)* scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("title", scri.getTitle());
		
		ArrayList<BoardVo> blist =  bm.boardSelectAll(hm);		
		return blist;
	}
	@Override
	public int boardTotalCount(SearchCriteria scri) {	
				
		//ÄÁÆ® 
		int cnt = bm.boardTotalCount(scri);
		return cnt;
	}

	@Override
	public BoardVo boardSelectOne(int bidx) {
		BoardVo bv = bm.boardSelectOne(bidx);		
		return bv;
	}


	@Override
	public int boardViewCntUpdate(int bidx) {
		int cnt = bm.boardViewCntUpdate(bidx);
		return cnt;
	}

	@Override
	//@Transactional
	public int boardInsert(BoardVo bv) {
	
		int value = bm.boardInsert(bv);
		int maxBidx = bv.getBidx();
		
		return value;
	}

	@Override
	//@Transactional
	    public int boardDelete(int bidx) {
	     
	        return bm.boardDelete(bidx);
	    }

	@Override
	public int boardUpdate(BoardVo bv) {
		
		return bm.boardUpdate(bv);
	}

	
		
}
