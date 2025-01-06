package com.recomedi.myapp.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recomedi.myapp.domain.MemberVo;
import com.recomedi.myapp.persistance.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	private MemberMapper mm;

	@Autowired
	public MemberServiceImpl(SqlSession sqlSession) {
		this.mm = sqlSession.getMapper(MemberMapper.class);
	}
	
	@Override
	public int memberInsert(MemberVo mv) {
		int value = mm.memberInsert(mv);
		return value;
	}

	@Override
	public int memberIdCheck(String id) {
		int value = mm.memberIdCheck(id);
		return value;
	}
	
	@Override
	public MemberVo memberSelect(String id) {
		
		MemberVo mv = mm.memberSelect(id);
		return mv;
	}

	@Override
	public int memberUpdate(MemberVo mv) {
		
		int value = mm.memberUpdate(mv);
		return value;
	}

	@Override
	public int memberDelete(MemberVo mv) {
		
		int value = mm.memberDelete(mv);
		return value;
	}

	@Override
	public String memberFindId(MemberVo mv) {

		String id = mm.memberFindId(mv);
		return id;
	};

	@Override
	public MemberVo memberFindPw(MemberVo mv) {

		MemberVo findMv = mm.memberFindPw(mv);
		
		return findMv;
	};

}
