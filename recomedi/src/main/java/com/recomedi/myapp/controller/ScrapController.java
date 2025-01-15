package com.recomedi.myapp.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.recomedi.myapp.domain.SearchCriteria;
import com.recomedi.myapp.domain.PageMaker;
import com.recomedi.myapp.domain.ScrapVo;
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
		
		logger.info("scrapList����");		

		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx);
		
		pm.setScri(scri);  // <-- PageMaker�� SearhCriteria ��Ƽ� ������ �ٴѴ�
		
		// ����¡ ó���ϱ� ���� ��ü ������ ���� ��������
		int cnt = scrapService.scrapTotalCount(scri, midx_int);
		
		pm.setTotalCount(cnt);  // <-- PageMaker�� ��ü�Խù����� ��Ƽ� ������ ���
				
		ArrayList<ScrapVo> slist = scrapService.scrapSelectAll(scri, midx_int);
		model.addAttribute("slist", slist);
		model.addAttribute("pm", pm);
		
		return "WEB-INF/scrap/scrapList";
	}
	
	@ResponseBody
	@RequestMapping(value="/scrapDeleteAction.do", method=RequestMethod.GET)
	public JSONObject scrapDeleteAction(
			@RequestParam("checkArr") String checkArr,
			RedirectAttributes rttr
			) {
		
		logger.info("scrapDeleteAction����");
		
		String[] sidxArr = checkArr.split(",");
		
		int[] sidxArr_int = new int[sidxArr.length];
		
		for(int i = 0; i < sidxArr.length; i++){
			sidxArr_int[i] = Integer.parseInt(sidxArr[i]);
		}
				
		int value = scrapService.scrapDelete(sidxArr_int);
		
		JSONObject js = new JSONObject();

		js.put("value", value);

		return js;
	}
	
	
//	@RequestMapping(value="/{returnUrl}/scrapDeleteAction.do", method=RequestMethod.GET)
//	public String scrapDeleteAction(
//			@PathVariable("returnUrl") String returnUrl,
//			@RequestParam("checkArr") String checkArr,
//			RedirectAttributes rttr
//			) {
//		
//		logger.info("scrapDeleteAction����");
//		
//		String[] sidxArr = checkArr.split(",");
//		
//		int[] sidxArr_int = new int[sidxArr.length];
//		
//		for(int i = 0; i < sidxArr.length; i++){
//			sidxArr_int[i] = Integer.parseInt(sidxArr[i]);
//		}
//				
//		int value = scrapService.scrapDelete(sidxArr_int);
//		
//		if(value > 0) {
//			rttr.addFlashAttribute("msg", "������ �Ϸ�Ǿ����ϴ�.");
//		} else {
//			rttr.addFlashAttribute("msg", "������ �����߽��ϴ�.");
//		}
//		
//		String path = "";
//		if(returnUrl.equals("scrapList")) {
//			path = "redirect:/scrap/" + returnUrl + ".do";
//		} else {
//			path = "redirect:/medicine/" + returnUrl + "/medicineContents.do";
//		}
//		return path;
//	}
	
	@ResponseBody
	@RequestMapping(value="/scrapInsertAction.do", method=RequestMethod.GET)
	public JSONObject scrapInsertAction(
			ScrapVo sv,
			Model model,
			HttpServletRequest request,
			RedirectAttributes rttr
			) throws Exception {
		
		logger.info("scrapInsertAction����");
		
		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx);
    	sv.setMidx(midx_int);

	    // �Ǿ���ǰ�ڵ�� midx�� sidx ã��
		Integer sidx = scrapService.findSidx(sv.getItemSeq(), midx_int, "All");

		String ip = userip.getUserIp(request);
		sv.setIp(ip);
		
		int value = 0;
		
	    if(sidx == null) {
	    	sidx = scrapService.scrapInsert(sv);
	    } else {
			sv.setSidx(sidx);
	    	value = scrapService.scrapUpdate(sv);
	    }
	    
		JSONObject js = new JSONObject();

		js.put("sidx", sidx);

		return js;
	}
}
