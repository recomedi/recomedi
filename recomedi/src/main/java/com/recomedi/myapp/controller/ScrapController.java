package com.recomedi.myapp.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.recomedi.myapp.domain.SearchCriteria;
import com.recomedi.myapp.domain.PageMaker;
import com.recomedi.myapp.domain.ScrapDto;
import com.recomedi.myapp.service.ScrapService;
import com.recomedi.myapp.util.UserIp;

@Controller
@RequestMapping(value="/scrap")
public class ScrapController {
	
	private static final Logger logger = LoggerFactory.getLogger(ScrapController.class);
	
	@Autowired(required=false)
	private ScrapService scrapService;
	
	@Autowired(required=false)
	private PageMaker pm;
	
	@Autowired(required=false)
	private UserIp userip;
	
	@RequestMapping(value="/scrapList.do", method=RequestMethod.GET)
	public String scrapList(
			SearchCriteria scri, 
			Model model, 
			HttpServletRequest request
			) {
		
		logger.info("scrapList들어옴");		

		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx);		
		
		pm.setScri(scri);  // <-- PageMaker에 SearhCriteria 담아서 가지고 다닌다
		
		// 페이징 처리하기 위한 전체 데이터 갯수 가져오기
		int cnt = scrapService.scrapTotalCount(scri, midx_int);
		
		pm.setTotalCount(cnt);  // <-- PageMaker에 전체게시물수를 담아서 페이지 계산
				
		ArrayList<ScrapDto> slist = scrapService.scrapSelectAll(scri, midx_int);
		model.addAttribute("slist", slist);
		model.addAttribute("pm", pm);
		
		return "WEB-INF/scrap/scrapList";
	}
	
	@RequestMapping(value="/scrapDeleteAction.do", method=RequestMethod.GET)
	public String scrapDeleteAction(
			@RequestParam("checkArr") String checkArr,
			HttpServletRequest request,
			RedirectAttributes rttr
			) {
		
		logger.info("scrapDeleteAction들어옴");
		
		String[] sidxArr = checkArr.split(",");
		
		int[] sidxArr_int = new int[sidxArr.length];

		for(int i = 0; i < sidxArr.length; i++){
			sidxArr_int[i] = Integer.parseInt(sidxArr[i]);
		}
		
		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx);
				
		int value = scrapService.scrapDelete(sidxArr_int, midx_int);
		
		if(value == sidxArr.length) {
			rttr.addFlashAttribute("msg", "스크랩 삭제가 완료되었습니다.");
		} else {
			rttr.addFlashAttribute("msg", "스크랩 삭제가 실패했습니다.");
		}
		
		return "redirect:/scrap/scrapList.do";
	}
}
