package com.lge.cto.swinfra.tiger.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lge.cto.swinfra.tiger.vo.AvgClienttimeDailyVO;
import com.lge.cto.swinfra.tiger.vo.AvgClienttimeHourlyVO;

/**
 * IP 대역별 통계 관련 DAO
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@Repository
public class ClienttimeDao {

    private final String namespace = "com.lge.cto.swinfra.tiger.clienttime";

    @Autowired
    SqlSession sqlSession;

    public List<AvgClienttimeHourlyVO> retrieveAvgLatencyHourlyClienttime(
            Map<String, String> params) {
        return sqlSession.selectList(String.format("%s.retrieveAvgRsHourlyClienttime", namespace),
                params);
    }

    public List<AvgClienttimeDailyVO> retrieveAvgRsDailyClienttime(Map<String, String> params) {
        return sqlSession.selectList(String.format("%s.retrieveAvgRsDailyClienttime", namespace),
                params);
    }

}
