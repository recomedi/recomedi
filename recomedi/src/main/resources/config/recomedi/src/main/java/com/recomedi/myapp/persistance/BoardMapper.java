package com.recomedi.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.recomedi.myapp.domain.BoardVo;
import com.recomedi.myapp.domain.SearchCriteria;

public interface BoardMapper {
	
	public ArrayList<BoardVo> boardSelectAll(HashMap<String,Object> hm) ;
	public int boardTotalCount(SearchCriteria scri) ;
	public int boardViewCntUpdate(int bidx);
	public BoardVo boardSelectOne(int bidx);
	public int boardInsert(BoardVo bv);
	public int boardDelete(int bidx);
	public int boardUpdate(BoardVo bv);
	
	
	
	
}
