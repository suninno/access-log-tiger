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

import com.lge.cto.swinfra.tiger.dao.IPRangeDao;
import com.lge.cto.swinfra.tiger.domain.AvgLatencyOfIPRangeDto;
import com.lge.cto.swinfra.tiger.domain.HttpStatusOfIPRangeDto;
import com.lge.cto.swinfra.tiger.util.DateUtil;
import com.lge.cto.swinfra.tiger.vo.AvgLatencyOfIPRangeVO;

/**
 * IP 대역별 관련 서비스
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@Service
@Transactional(readOnly = true)
public class IPRangeService extends ServiceRoot {

    private static final Logger logger = LoggerFactory.getLogger(IPRangeService.class);

    @Autowired
    private IPRangeDao iprangeDao;

    /**
     * IP 대역별 Http Status 통계
     * service: jira3, collab1은 하드코딩
     * @param date		검색 일자
     * @param logType	apache or tomcat
     * @return	해당하는 데이터의 DTO List
     */
    public List<HttpStatusOfIPRangeDto> retrieveHttpStatusOfIPRange(String date, String logType) {

        DateUtil.isValidDateParameters(date, DateUtil.YMD_FORMAT);

        if (!(logType.equals("apache") || logType.equals("tomcat"))) {
            return new ArrayList<HttpStatusOfIPRangeDto>();
        }

        Map<String, String> params = newHashMap();
        params.put("date", date);
        params.put("logType", logType);

        return iprangeDao.retrieveHttpStatusOfIPRange(params);
    }

    /**
     * IP 대역별 시간별 평균 지연
     * @param date	검색 일자
     * @param logType	apache or tomcat
     * @param service	jira3 or collab1
     * @return	해당하는 데이터의 DTO List
     */
    public List<AvgLatencyOfIPRangeDto> retrieveAvgRestofIPRange(String date, String logType,
            String service) {

        DateUtil.isValidDateParameters(date, DateUtil.YMD_FORMAT);

        if (!(logType.equals("apache") || logType.equals("tomcat"))) {
            return new ArrayList<AvgLatencyOfIPRangeDto>();
        }

        if (!(service.equals("jira3") || service.equals("collab1"))) {
            return new ArrayList<AvgLatencyOfIPRangeDto>();
        }

        Map<String, String> params = newHashMap();
        params.put("date", date);
        params.put("logType", logType);
        params.put("service", service);
        params.put("status1", "200");
        params.put("status2", "400");

        //Key: HH
        Map<String, AvgLatencyOfIPRangeDto> res = new TreeMap<String, AvgLatencyOfIPRangeDto>();

        List<AvgLatencyOfIPRangeVO> list = iprangeDao.retrieveAvgLatencyHourlyofIPRange(params);
        for (AvgLatencyOfIPRangeVO e : list) {

            AvgLatencyOfIPRangeDto el = res.get(e.getHh());
            if (el == null) {
                el = new AvgLatencyOfIPRangeDto(e.getHh());
                res.put(e.getHh(), el);
            }

            switch(e.getIp_subset().toLowerCase()){
            case IPSubNet.MC_A:
            	el.setMcaLatency(e.getAvgLatency());
            	break;
            
            case IPSubNet.MC_C:
                el.setMccLatency(e.getAvgLatency());
            	break;
            	
            case IPSubNet.OTHERS:
            	el.setOthersLatency(e.getAvgLatency());
            	break;

        	default:
              String msg = "Unknow ip_subset(" + e.getIp_subset() + ")";
              logger.error(msg);
              throw new RuntimeException(msg);
            
            }
        }

        return new ArrayList<AvgLatencyOfIPRangeDto>(res.values());
    }

}
