package com.recomedi.myapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	private final MedicineService medicineService;
	
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
		
		MedicineVo mdv = medicineService.medicineSelectOne(medidx);
		model.addAttribute("mdv",mdv);
		
		String path = "WEB-INF/board/medicineCOntents";
		
		return path;
	}
	
	public MedicineController() {
		this.medicineService = new MedicineServiceImpl();
	}
	
	
	
	
    public void getMedicineData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pageNo = request.getParameter("pageNo");
        String numOfRows = request.getParameter("numOfRows");

        List<MedicineVo> medicineList = medicineService.getMedicineData(pageNo, numOfRows);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < medicineList.size(); i++) {
            MedicineVo medicine = medicineList.get(i);
            json.append("{");
            json.append("\"itemName\":\"").append(medicine.getItemName()).append("\",");
            json.append("\"entpName\":\"").append(medicine.getEntpName()).append("\",");
            json.append("\"efcyQesitm\":\"").append(medicine.getEfcyQesitm()).append("\"");
            json.append("}");
            if (i < medicineList.size() - 1) json.append(",");
        }
        json.append("]");

        response.getWriter().write(json.toString());
    }
}