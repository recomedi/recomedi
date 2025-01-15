package com.recomedi.myapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recomedi.myapp.service.CommentService;
import com.recomedi.myapp.util.UserIp;

@RestController
@RequestMapping(value="/comment")
public class CommentController {

//	@Autowired(required=false)
//	CommentService commentService;
//
//	@Autowired(required=false)
//	private UserIp userip;
	
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	
	
}
