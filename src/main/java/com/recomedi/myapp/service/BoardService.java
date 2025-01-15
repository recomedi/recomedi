package com.recomedi.myapp.service;
import java.util.ArrayList;
import com.recomedi.myapp.domain.BoardVo;
import com.recomedi.myapp.domain.SearchCriteria;
/*�������̽��� �ż��带 ��Ƽ� ��Ʈ�ѷ����� ������ �Ǹ��� ����� �Ű��������� ����ɼ� �ֵ���
�Ѵ�.*/
public interface BoardService {
	
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri);	
	public int boardTotalCount(SearchCriteria scri);
	public int boardViewCntUpdate(int bidx);
	public BoardVo boardSelectOne(int bidx);
	public int boardInsert(BoardVo bv);
	public int boardDelete(int bidx);
	public int boardUpdate(BoardVo bv);
	
	
	
}