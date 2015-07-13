package com.lge.cto.swinfra.tiger.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lge.cto.swinfra.tiger.domain.AvgRestofUriDto;
import com.lge.cto.swinfra.tiger.domain.HttpStatusDto;
import com.lge.cto.swinfra.tiger.domain.ShortPeriodDataofUriDto;
import com.lge.cto.swinfra.tiger.domain.UriPathDto;
import com.lge.cto.swinfra.tiger.vo.AvgRestofUriVO;

/**
 * URI 통계 관련 DAO
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@Repository
public class UriDao {

    //	private static final Logger logger = LoggerFactory.getLogger(UriDao.class);

    private final String namespace = "com.lge.cto.swinfra.tiger";
    private final String adaynamespace = "com.lge.cto.swinfra.tiger.aday";

    @Autowired
    SqlSession sqlSession;

    public List<UriPathDto> retrieveRESTApiSummaryDaily(Map<String, String> params) {
        return sqlSession.selectList("com.lge.cto.swinfra.tiger.getcalledCountDaily", params);
    }

    public List<HttpStatusDto> retreiveHttpStatusDaily(Map<String, String> params) {
        return sqlSession.selectList(String.format("%s.getHttpStatusDaily", namespace), params);
    }

    public long retrieveLatencyDaily(Map<String, String> params) {
        return sqlSession.selectOne(String.format("%s.getLatencyDaily", namespace), params);
    }

    public long retrieveLatencyDailyMin(Map<String, String> params) {
        return sqlSession.selectOne(String.format("%s.getLatencyDailyMin", namespace), params);
    }

    public long retrieveLatencyDailyMax(Map<String, String> params) {
        return sqlSession.selectOne(String.format("%s.getLatencyDailyMax", namespace), params);
    }

    public List<AvgRestofUriDto> retrieveAvgRestofUri(Map<String, String> params) {
        return sqlSession.selectList(String.format("%s.getUriDetail", adaynamespace), params);
    }

    
    public List<ShortPeriodDataofUriDto> retrieveShortPeriodDataofUriPerMinute(Map<String, String> params) {
        return sqlSession.selectList(String.format("%s.getPeriodUriDetailPerMinute", adaynamespace), params);
    }
    
    public List<ShortPeriodDataofUriDto> retrieveShortPeriodDataofUriPerHour(Map<String, String> params) {
	        return sqlSession.selectList(String.format("%s.getPeriodUriDetailPerHour", adaynamespace), params);
    }
	    	
    public List<ShortPeriodDataofUriDto> retrieveShortPeriodDataofUriPerDay(Map<String, String> params) {
        return sqlSession.selectList(String.format("%s.getPeriodUriDetailPerDay", adaynamespace), params);
    }
    
    
    public List<AvgRestofUriVO> retrieveUriHistory(Map<String, String> params){
    	return sqlSession.selectList(String.format("%s.getUriHistory", adaynamespace), params);
    }

    public List<AvgRestofUriDto> retrieveAvgRestofUri2(Map<String, String> params) {
        return sqlSession.selectList(String.format("%s.getUriDetail2", adaynamespace), params);
    }

}
