package com.recomedi.myapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.recomedi.myapp.domain.MedicineVo;
import com.recomedi.myapp.domain.PageMaker;
import com.recomedi.myapp.domain.SearchCriteria;
import com.recomedi.myapp.service.MedicineService;
import com.recomedi.myapp.service.MedicineServiceImpl;
import com.recomedi.myapp.util.MedicineInfo;

@Controller
@RequestMapping(value="/medicine/")
public class MedicineController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MedicineService medicineService;
	
	@Autowired(required = false)
	private PageMaker pm;
	
	@RequestMapping(value="medicineList.do")
	public String medicineList(SearchCriteria scri, Model model) throws IOException {
		logger.info("medicineList에 들어옴");
		
		pm.setScri(scri);
		
		String pageNo = pm.getScri().getPage() + "";
		String numOfRows = pm.getScri().getPerPageNum() + "";
		String searchType = pm.getScri().getSearchType() + "";
		String keyword = pm.getScri().getKeyword() + "";
		
		MedicineInfo medicineInfo = new MedicineInfo("List", pageNo, numOfRows, searchType, keyword);
		String medicineInfoString = medicineInfo.getMedicineInfo();
		
		JSONObject jsonObject = new JSONObject(medicineInfoString);
		JSONObject body = jsonObject.getJSONObject("body");
		
		int totalCount = body.getInt("totalCount");
		pm.setTotalCount(totalCount);
		
		if(totalCount>0) {
			JSONArray items = body.getJSONArray("items");
			ArrayList<MedicineVo> mlist = new ArrayList<>();
			
			for(int i = 0 ; i < items.length() ; i++) {
				JSONObject item = items.getJSONObject(i);
				String itemName = item.getString("itemName");
				String entpName = item.getString("entpName");
				String itemSeq = item.getString("itemSeq");
				
				MedicineVo mdv = new MedicineVo();
				mdv.setItemName(itemName);
				mdv.setEntpName(entpName);
				mdv.setItemSeq(itemSeq);
				mlist.add(mdv);
;			}
			
			model.addAttribute("mlist", mlist);
			model.addAttribute("keyword",keyword);
		}

		model.addAttribute("pm", pm);
		
		
		return "WEB-INF/medicine/medicineList";
	}
	
//	@RequestMapping(value="medicineContents.do")		
//  	public String medicineContents(@RequestParam("medidx") int medidx, Model model) {
//		logger.info("medicinContents에 들어옴");
//		
//		MedicineVo mdv = medicineService.medicineSelectOne(medidx);
//		model.addAttribute("mdv",mdv);
//		logger.info("mdv" + mdv);
//		
//		String path = "WEB-INF/medicine/medicineContents";
//		
//		return path;
//	}
	
	@RequestMapping(value="medicineHashTag.do")
	public String medicineHashTag(Model model) {

	    // 해시태그 리스트 정의
	    List<String> hashTags = Arrays.asList("감기", "소화", "해열", "진통", "피부", "속쓰림", "구충", "불면증", "육체피로");

	    // 해시태그별 검색 결과를 Map에 담기
	    Map<String, List<MedicineVo>> mlist = new HashMap<>();
	    for (String tag : hashTags) {
	        List<MedicineVo> medicines = medicineService.medicineHashTag(tag);
	        mlist.put(tag, medicines);
	    }
	    

	    // 모델에 데이터 추가
	    model.addAttribute("mlist", mlist);
	    
	    return "WEB-INF/medicine/medicineHashTag";
	}
	
	@RequestMapping(value = "medicineHashTagMore.do")
	public String medicineHashTagMore(@RequestParam("hashTag") String hashTag, Model model) {
		logger.info("hashTagMore에들어옴");
		List<MedicineVo> hmlist = medicineService.medicineHashTag(hashTag);
		
		model.addAttribute("hmlist", hmlist);
		model.addAttribute("hashTag", hashTag);
		logger.info("hashTagMore에들어옴 " + hashTag );
		
		String path ="WEB-INF/medicine/medicineList";
		
		return path;
	}
	
	@RequestMapping(value="{itemSeq}/medicineContents.do")
	public String medicineContents(
			@PathVariable("itemSeq") String itemSeq,
			Model model) throws IOException {
		
		logger.info("medicineContents들어옴");
	
		// API에서 데이터 가져오기
//		MedicineInfo medicineInfo = new MedicineInfo();
//		String medicineInfoString = medicineInfo.getMedicineInfo(itemSeq);
		MedicineInfo medicineInfo = new MedicineInfo("contents", itemSeq);
		String medicineInfoString = medicineInfo.getMedicineInfo();
		
		// JSON 파싱
        JSONObject jsonObject = new JSONObject(medicineInfoString);
        JSONObject body = jsonObject.getJSONObject("body");
        JSONArray items = body.getJSONArray("items");

        MedicineVo mdv = new MedicineVo();
        
        // items 배열에서 값 추출 후 Vo에 담기
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String itemName = item.getString("itemName");
            String entpName = item.getString("entpName");
//            String efcyQesitm = item.getString("efcyQesitm");
//            String useMethodQesitm = item.getString("useMethodQesitm");
//            String atpnWarnQesitm = item.getString("atpnWarnQesitm");
//            String atpnQesitm = item.getString("atpnQesitm");
//            String intrcQesitm = item.getString("intrcQesitm");
//            String seQesitm = item.getString("seQesitm");
//            String depositMethodQesitm = item.getString("depositMethodQesitm");   
            
            // String efcyQesitm = item.optString("efcyQesitm", "N/A"); // null 방지
            
            String efcyQesitm = item.optString("efcyQesitm", "N/A");            
            String useMethodQesitm = item.optString("useMethodQesitm", "N/A");
            String atpnWarnQesitm = item.optString("atpnWarnQesitm", "N/A");
            String atpnQesitm = item.optString("atpnQesitm", "N/A");
            String intrcQesitm = item.optString("intrcQesitm", "N/A");
            String seQesitm = item.optString("seQesitm", "N/A");
            String depositMethodQesitm = item.optString("depositMethodQesitm", "N/A");

            mdv.setItemName(itemName);
            mdv.setEntpName(entpName);
            mdv.setEfcyQesitm(efcyQesitm);
            mdv.setUseMethodQesitm(useMethodQesitm);
            mdv.setAtpnWarnQesitm(atpnWarnQesitm);
            mdv.setAtpnQesitm(atpnQesitm);
            mdv.setIntrcQesitm(intrcQesitm);
            mdv.setSeQesitm(seQesitm);
            mdv.setDepositMethodQesitm(depositMethodQesitm);
        }
        
	    // jsp로 Vo 보내기
	    model.addAttribute("mdv", mdv);
	    
	    return "WEB-INF/medicine/medicineContents";
	}
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