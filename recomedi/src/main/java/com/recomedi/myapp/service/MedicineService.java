package com.recomedi.myapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.recomedi.myapp.domain.MedicineVo;
import com.recomedi.myapp.domain.SearchCriteria;


public interface MedicineService {
//    List<MedicineVo> getMedicineData(String pageNo, String numOfRows);
    public int medicineListTotalCount(SearchCriteria scri);
    public MedicineVo medicineSelectOne(int medidx);
    public ArrayList<MedicineVo> medicineSelectAll(SearchCriteria scri);
    public List<MedicineVo> medicineHashTag(String hashTag);
    public List<MedicineVo> medicineHashTagMore(String hashTag);
}

