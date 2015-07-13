package com.lge.cto.swinfra.tiger.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lge.cto.swinfra.tiger.vo.AvgRestofUriVO;

/**
 * 기간 지연 관련 DAO
 * @author suninno.kim@lge.com
 * @date 2014. 5. 16
 */
@Repository
public class LatencyDao {

    private final String namespace = "com.lge.cto.swinfra.tiger.latency";

    @Autowired
    SqlSession sqlSession;

    /**
     * 각 서비스에 있어서의 총 지연 정보를 분단위로 취함
     * @param params
     * @return
     */
    public List<AvgRestofUriVO> retrieveLatencyperMin(Map<String, String> params) {
    	return sqlSession.selectList(String.format("%s.retrieveLatencyperMin", namespace), params);
    }
    
    /**
     * 각 서비스에 있어서의 총 지연 정보를 시간단위로 취함 
     * @param params
     * @return
     */
    public List<AvgRestofUriVO> retrieveLatencyperHour(Map<String, String> params) {
    	return sqlSession.selectList(String.format("%s.retrieveLatencyperHour", namespace), params);
    }
    
    public List<AvgRestofUriVO> retrieveLatencyPerDay(Map<String, String> params){
    	return sqlSession.selectList(String.format("%s.retrieveLatencyperDay", namespace), params);
    }
}
