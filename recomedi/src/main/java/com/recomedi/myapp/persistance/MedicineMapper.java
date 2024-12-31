package com.recomedi.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.recomedi.myapp.domain.MedicineVo;
import com.recomedi.myapp.domain.SearchCriteria;

public interface MedicineMapper {
	
	public int medicineListTotalCount(SearchCriteria scri);
	public ArrayList<MedicineVo> medicineSelectAll(HashMap<String,Object> hm);
	public MedicineVo medicineSelectOne(int medidx);
	
}