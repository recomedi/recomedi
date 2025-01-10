package com.recomedi.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.recomedi.myapp.domain.PrescriptionVo;
import com.recomedi.myapp.persistance.PrescriptionMapper;

public interface PrescriptionService {


	// 회원별 처방 목록 조회
    List<PrescriptionVo> getPrescriptionsByMember(int midx);

    // 특정 처방전 상세 정보 조회
    PrescriptionVo getPrescriptionDetail(int pidx);
    
    
}
