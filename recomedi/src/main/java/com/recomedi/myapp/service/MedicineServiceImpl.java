package com.recomedi.myapp.service;

import com.recomedi.myapp.domain.MedicineVo;
import com.recomedi.myapp.domain.SearchCriteria;
import com.recomedi.myapp.persistance.MedicineMapper;
import com.recomedi.myapp.util.ApiUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final ApiUtil apiUtil;
    private MedicineMapper mdm;
    
    public MedicineServiceImpl() {
    	this.apiUtil = new ApiUtil();
    }

    @Override
    public List<MedicineVo> getMedicineData(String pageNo, String numOfRows) {
        String response = apiUtil.callpublicApi(pageNo, numOfRows);

        List<MedicineVo> medicineList = new ArrayList<>();
        if (response != null) {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray items = jsonResponse.getJSONObject("response").getJSONObject("body").getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);

                MedicineVo medicine = new MedicineVo();
                medicine.setItemName(item.getString("itemName"));
                medicine.setEntpName(item.getString("entpName"));
                medicine.setEfcyQesitm(item.getString("efcyQesitm"));

                medicineList.add(medicine);
            }
        }

        return medicineList;
    }
    
    @Override
    public int medicineListTotalCount(SearchCriteria scri) {
    	
    	int cnt = mdm.medicineListTotalCount(scri);
    	
    	return cnt;
    	
    }

	@Override
	public ArrayList<MedicineVo> medicineSelectAll(SearchCriteria scri) {

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("startPageNum",(scri.getPage()-1)*scri.getPerPageNum());
		hm.put("perPageNum", scri.getPerPageNum());
		hm.put("searchType", scri.getSearchType());
		hm.put("keyword", scri.getKeyword());
		
		ArrayList<MedicineVo> mlist = mdm.medicineSelectAll(hm);
		
		return mlist;
	}

	@Override
	public MedicineVo medicineSelectOne(int medidx) {

		MedicineVo mdv = mdm.medicineSelectOne(medidx);
		
		return mdv;
	}
    
}
 