package com.recomedi.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.recomedi.myapp.domain.MedicineVo;
import com.recomedi.myapp.domain.SearchCriteria;

public interface MedicineMapper {
	
	public int medicineListTotalCount(SearchCriteria scri);
	public ArrayList<MedicineVo> medicineSelectAll(HashMap<String,Object> hm);
	public MedicineVo medicineSelectOne(int medidx);
	public List<MedicineVo> medicineHashTag(@Param("hashTag") String hashTag);
}