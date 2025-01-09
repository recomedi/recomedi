package com.recomedi.myapp.service;

import java.util.ArrayList;

import com.recomedi.myapp.domain.BoardVo;
import com.recomedi.myapp.domain.SearchCriteria;

/*인터페이스는 매서드를 담아서 컨트롤러에서 선언이 되면은 선언된 매개변수들을 실행될수 있도록
한다.*/
public interface BoardService {
	
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri);	
	public int boardTotalCount(SearchCriteria scri);
	public int boardViewCntUpdate(int bidx);
	public BoardVo boardSelectOne(int bidx);
	public int boardInsert(BoardVo bv);
	public int boardDelete(int bidx);
	public int boardUpdate(BoardVo bv);
	
	
	
}
