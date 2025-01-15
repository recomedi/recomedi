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
        
        // CodefToken ���
//		        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//		        con.setRequestProperty("Authorization", "Bearer " + token);

		
		return "WEB-INF/main";
	}
}