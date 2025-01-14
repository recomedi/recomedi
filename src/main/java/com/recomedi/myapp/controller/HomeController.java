package com.recomedi.myapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.recomedi.myapp.api.EasyCodefToken;
import com.recomedi.myapp.service.BoardService;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired(required=false)
	private BoardService boardService;
	
	@RequestMapping(value = "/main.do")
	public String mainPage(Model model) {
		
		logger.info("main들어옴");
		
		
		// CodefToken 생성
		String clientId = "fbbcf915-2395-4dfe-9316-a5ce610fab1a";
		String clientSecret = "2b152335-b63a-4596-bf34-5b44f79b41b0";

		// CodefTokenExample 객체 생성
		EasyCodefToken easyCodefToken = new EasyCodefToken();
        
        // getAccessToken 호출
        String accessToken = easyCodefToken.getAccessToken(clientId, clientSecret);
        
        // 결과 출력
        System.out.println("Access Token: " + accessToken);
        
        // CodefToken 사용
//		        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//		        con.setRequestProperty("Authorization", "Bearer " + token);

		
		return "WEB-INF/main";
	}
}