package com.recomedi.myapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.recomedi.myapp.domain.PageMaker;
import com.recomedi.myapp.service.BoardService;
import com.recomedi.myapp.util.UserIp;

@Controller
@RequestMapping(value="/board")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
//	@Autowired(required=false)
//	private BoardService boardService;
//		
//	@Autowired(required=false)
//	private PageMaker pm;
//	
//	@Autowired(required=false)
//	private UserIp userip;
	
	// aaaaaa
}
