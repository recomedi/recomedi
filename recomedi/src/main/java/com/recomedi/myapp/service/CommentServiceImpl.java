package com.recomedi.myapp.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recomedi.myapp.persistance.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentMapper cm;
	
	@Autowired
	public CommentServiceImpl(SqlSession sqlSession) {
		this.cm = sqlSession.getMapper(CommentMapper.class);
	}
	
}
