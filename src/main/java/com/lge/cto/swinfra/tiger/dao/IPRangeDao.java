package com.lge.cto.swinfra.tiger.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lge.cto.swinfra.tiger.domain.HttpStatusOfIPRangeDto;
import com.lge.cto.swinfra.tiger.vo.AvgLatencyOfIPRangeVO;

/**
 * IP 대역별 통계 관련 DAO
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@Repository
public class IPRangeDao {

    private final String namespace = "com.lge.cto.swinfra.tiger.latency";

    @Autowired
    SqlSession sqlSession;

    public List<HttpStatusOfIPRangeDto> retrieveHttpStatusOfIPRange(Map<String, String> params) {
        return sqlSession.selectList(String.format("%s.retrieveHttpStatusofIPRange", namespace),
                params);
    }

    public List<AvgLatencyOfIPRangeVO> retrieveAvgLatencyHourlyofIPRange(Map<String, String> params) {
        return sqlSession.selectList(
                String.format("%s.retrieveAvgLatencyHourlyofIPRange", namespace), params);
    }

}
