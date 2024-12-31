package com.recomedi.myapp.service;

import java.util.ArrayList;
import java.util.List;


import com.recomedi.myapp.domain.MedicineVo;
import com.recomedi.myapp.domain.SearchCriteria;


public interface MedicineService {
    List<MedicineVo> getMedicineData(String pageNo, String numOfRows);
    
    public int medicineListTotalCount(SearchCriteria scri);
    public ArrayList<MedicineVo> medicineSelectAll(SearchCriteria scri);
    public MedicineVo medicineSelectOne(int medidx);
    
}