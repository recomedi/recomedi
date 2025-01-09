package com.recomedi.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.recomedi.myapp.domain.MemberVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {
		
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();
		
		if(session.getAttribute("midx") != null) {
			session.removeAttribute("midx");
			session.removeAttribute("id");
			session.removeAttribute("admin");
		}
		
		return true;
	}
	
	@Override
	public void postHandle(
	    HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
	    throws Exception {

	    HttpSession session = request.getSession();
	    
	    // modelAndView의 모델을 출력해서 확인
	    System.out.println("Model: " + modelAndView.getModel());

	    // 세션에 저장할 MemberVo 객체 생성
	    MemberVo sessionMember = new MemberVo();
	    
	    // "midx" 값을 String에서 int로 변환
	    String midxStr = (String) modelAndView.getModel().get("midx");
	    int midx = (midxStr != null) ? Integer.parseInt(midxStr) : 0;  // null 체크 후 int로 변환
	    
	    sessionMember.setMidx(midx);  // setMidx()에 int 타입 전달
	    sessionMember.setId((String) modelAndView.getModel().get("id"));
	    sessionMember.setAdmin((String) modelAndView.getModel().get("admin"));

	    // 세션에 저장
	    session.setAttribute("sessionMember", sessionMember);
	    
	    // 세션에 저장된 값을 로그로 출력해서 확인
	    System.out.println("Session Member: " + session.getAttribute("sessionMember"));
	}
}
