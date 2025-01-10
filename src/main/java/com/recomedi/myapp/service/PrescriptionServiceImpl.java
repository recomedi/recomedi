package com.recomedi.myapp.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recomedi.myapp.domain.PrescriptionVo;
import com.recomedi.myapp.persistance.PrescriptionMapper;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

	
    private PrescriptionMapper pm;
    
    @Autowired
    public PrescriptionServiceImpl (SqlSession sqlSession) {
    	this.pm =sqlSession.getMapper(PrescriptionMapper.class);
    }
    

    @Override
    public List<PrescriptionVo> getPrescriptionsByMember(int midx) {
        return pm.selectPrescriptionsByMember(midx);
    }

    @Override
    public PrescriptionVo getPrescriptionDetail(int pidx) {
        return pm.selectPrescriptionDetail(pidx);
    }

}
