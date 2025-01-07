package com.recomedi.myapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.recomedi.myapp.domain.MedicineVo;
import com.recomedi.myapp.domain.PageMaker;
import com.recomedi.myapp.domain.SearchCriteria;
import com.recomedi.myapp.service.MedicineService;
import com.recomedi.myapp.service.MedicineServiceImpl;

@Controller
@RequestMapping(value="/medicine/")
public class MedicineController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MedicineService medicineService;
	
	@Autowired(required = false)
	private PageMaker pm;
	
	@RequestMapping(value="medicineList.do")
	public String medicineList(SearchCriteria scri, Model model) {
		logger.info("medicineList에 들어옴");
		
		int cnt = medicineService.medicineListTotalCount(scri);
		pm.setScri(scri);
		pm.setTotalCount(cnt);
		
		ArrayList<MedicineVo> mlist = medicineService.medicineSelectAll(scri);
		
		model.addAttribute("mlist", mlist);
		model.addAttribute("pm", pm);
		
		String path = "WEB-INF/medicine/medicineList";
		return path;
	}
	
	@RequestMapping(value="medicineContents.do")		
  	public String medicineContents(@RequestParam("medidx") int medidx, Model model) {
		logger.info("medicinContents에 들어옴");
		
		MedicineVo mdv = medicineService.medicineSelectOne(medidx);
		model.addAttribute("mdv",mdv);
		logger.info("mdv" + mdv);
		
		String path = "WEB-INF/medicine/medicineContents";
		
		return path;
	}
	
	@RequestMapping(value="medicineHashTag.do")
	public String medicineHashTag(Model model) {

	    // 해시태그 리스트 정의
	    List<String> hashTags = Arrays.asList("감기", "소화제", "해열제", "진통제", "피부약", "속쓰림", "구충제", "불면증", "육체피로");

	    // 각 해시태그에 대한 검색 결과 가져오기
	    Map<String, List<MedicineVo>> hashTagResults = medicineService.medicineHashTag(hashTags);

	    // 모델에 데이터 추가
	    model.addAttribute("hashTagResults", hashTagResults);

	    // 해시태그 리스트 추가 (뷰에서 #해시태그 출력에 사용)
	    model.addAttribute("hashTags", hashTags);
		
		
		String path = "WEB-INF/medicine/medicineHashTag";
		return path;
	}
	
	
//    public void getMedicineData(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String pageNo = request.getParameter("pageNo");
//        String numOfRows = request.getParameter("numOfRows");
//
//        List<MedicineVo> medicineList = medicineService.getMedicineData(pageNo, numOfRows);
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        StringBuilder json = new StringBuilder("[");
//        for (int i = 0; i < medicineList.size(); i++) {
//            MedicineVo medicine = medicineList.get(i);
//            json.append("{");
//            json.append("\"itemName\":\"").append(medicine.getItemName()).append("\",");
//            json.append("\"entpName\":\"").append(medicine.getEntpName()).append("\",");
//            json.append("\"efcyQesitm\":\"").append(medicine.getEfcyQesitm()).append("\"");
//            json.append("}");
//            if (i < medicineList.size() - 1) json.append(",");
//        }
//        json.append("]");
//
//        response.getWriter().write(json.toString());
//    }
}