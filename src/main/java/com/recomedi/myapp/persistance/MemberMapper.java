package com.recomedi.myapp.persistance;

import com.recomedi.myapp.domain.MemberVo;

public interface MemberMapper {
	
	public int memberInsert(MemberVo mv);

	public int memberIdCheck(String id);

	public MemberVo memberSelect(String id);

	public int memberUpdate(MemberVo mv);

	public int memberDelete(MemberVo mv);
	
	public String memberFindId(MemberVo mv);
	
	public MemberVo memberFindPw(MemberVo mv);
	
}