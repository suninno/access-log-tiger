package com.lge.cto.swinfra.tiger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lge.cto.swinfra.tiger.dao.ClienttimeDao;
import com.lge.cto.swinfra.tiger.domain.AvgClienttimeDailyDto;
import com.lge.cto.swinfra.tiger.domain.AvgClienttimeHourlyDto;
import com.lge.cto.swinfra.tiger.util.DateUtil;
import com.lge.cto.swinfra.tiger.vo.AvgClienttimeDailyVO;
import com.lge.cto.swinfra.tiger.vo.AvgClienttimeHourlyVO;

/**
 * IP 대역별 관련 서비스
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@Service
@Transactional(readOnly = true)
public class ClienttimeService extends ServiceRoot {

    private static final Logger logger = LoggerFactory.getLogger(ClienttimeService.class);

    @Autowired
    private ClienttimeDao clienttimeDao;

    
    /**
     * Clienttime 시간별 평균 지연 구하기<br>
     * httpstatus가 [200,400)인 데이터만을 취급
     * @param date		검색 일자
     * @param service	jira3, collab1 or null (null일 경우, 모든 서비스를 대상으로 함.)
     * @return			대상 데이터를 {@link AvgClienttimeHourlyDto AvgClienttimeHourlyDto}의 Collection
     */
    public List<AvgClienttimeHourlyDto> retrieveAvgLatencyHourlyClienttime(String date,
            String service) {

        DateUtil.isValidDateParameters(date, DateUtil.YMD_FORMAT);

        if (!(service == null || service.equals("jira3") || service.equals("collab1"))) {
            return new ArrayList<AvgClienttimeHourlyDto>();
        }

        Map<String, String> params = newHashMap();
        params.put("date", date);
        params.put("service", service);
        params.put("status1", "200");
        params.put("status2", "400");

        //Key: HH
        Map<String, AvgClienttimeHourlyDto> res = new TreeMap<String, AvgClienttimeHourlyDto>();

        List<AvgClienttimeHourlyVO> list = clienttimeDao.retrieveAvgLatencyHourlyClienttime(params);
        for (AvgClienttimeHourlyVO e : list) {

            AvgClienttimeHourlyDto el = res.get(e.getKey());
            if (el == null) {
                el = new AvgClienttimeHourlyDto(e.getHh(), e.getService());
                res.put(el.getKey(), el);
            }

            switch(e.getIp_subset().toLowerCase()){
            
            case IPSubNet.MC_A:
            	el.setMCA(e);
                break;
            	
            case IPSubNet.MC_C:
            	el.setMCC(e);
                break;
            	
            case IPSubNet.OTHERS:
            	el.setOthers(e);
                break;
            	
            default:
              String msg = "Unknow ip_subset(" + e.getIp_subset() + ")";
              logger.error(msg);
              throw new RuntimeException(msg);
            		
            }
        }

        return new ArrayList<AvgClienttimeHourlyDto>(res.values());
    }

    /**
     * Clienttime 일별 평균 지연 구하기<br>
     * httpstatus가 [200,400)인 데이터만을 취급
     * @param sdate		검색 시작 일자(폐구간)
     * @param edate		검색 종료 일자(폐구간)
     * @param service	jira3, collab1 or null (null일 경우, 모든 서비스를 대상으로 함.)
     * @return			대상 데이터를 {@link AvgClienttimeDailyDto AvgClienttimeDailyDto}의 Collection 
     */
    public List<AvgClienttimeDailyDto> retrieveAvgRsDailyClienttime(String sdate, String edate,
            String service) {
        DateUtil.isValidDateParameters(sdate, DateUtil.YMD_FORMAT);
        DateUtil.isValidDateParameters(edate, DateUtil.YMD_FORMAT);

        Map<String, String> params = newHashMap();
        params.put("sdate", sdate);
        params.put("edate", edate);
        params.put("service", service);
        params.put("status1", "200");
        params.put("status2", "400");

        Map<String, AvgClienttimeDailyDto> res = new TreeMap<String, AvgClienttimeDailyDto>();

        List<AvgClienttimeDailyVO> list = clienttimeDao.retrieveAvgRsDailyClienttime(params);
        for (AvgClienttimeDailyVO e : list) {
            AvgClienttimeDailyDto el = res.get(e.getKey());

            if (el == null) {
                el = new AvgClienttimeDailyDto(e.getYmd(), e.getService());
                res.put(el.getKey(), el);
            }

            switch(e.getIp_subset().toLowerCase()){
            case IPSubNet.MC_A:
            	el.setMCA(e);
                break;
                
            case IPSubNet.MC_C:
            	el.setMCC(e);
                break;
                
            case IPSubNet.OTHERS:
            	el.setOthers(e);
                break;

            default:
                String msg = "Unknow ip_subset(" + e.getIp_subset() + ")";
                logger.error(msg);
                throw new RuntimeException(msg);
            }
        }

        return new ArrayList<AvgClienttimeDailyDto>(res.values());
    }
}
