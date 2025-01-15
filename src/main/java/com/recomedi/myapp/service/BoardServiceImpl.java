package com.recomedi.myapp.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.recomedi.myapp.persistance.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper bm;
	
	@Autowired
	public BoardServiceImpl(SqlSession sqlSession) {
		this.bm = sqlSession.getMapper(BoardMapper.class);
	}
	
}
