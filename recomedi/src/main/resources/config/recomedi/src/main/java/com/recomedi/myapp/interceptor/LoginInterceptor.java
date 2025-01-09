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
	    
	    // modelAndView�� ���� ����ؼ� Ȯ��
	    System.out.println("Model: " + modelAndView.getModel());

	    // ���ǿ� ������ MemberVo ��ü ����
	    MemberVo sessionMember = new MemberVo();
	    
	    // "midx" ���� String���� int�� ��ȯ
	    String midxStr = (String) modelAndView.getModel().get("midx");
	    int midx = (midxStr != null) ? Integer.parseInt(midxStr) : 0;  // null üũ �� int�� ��ȯ
	    
	    sessionMember.setMidx(midx);  // setMidx()�� int Ÿ�� ����
	    sessionMember.setId((String) modelAndView.getModel().get("id"));
	    sessionMember.setAdmin((String) modelAndView.getModel().get("admin"));

	    // ���ǿ� ����
	    session.setAttribute("sessionMember", sessionMember);
	    
	    // ���ǿ� ����� ���� �α׷� ����ؼ� Ȯ��
	    System.out.println("Session Member: " + session.getAttribute("sessionMember"));
	}
}
