package com.lge.cto.swinfra.tiger.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lge.cto.swinfra.tiger.dao.LatencyDao;
import com.lge.cto.swinfra.tiger.domain.AvgRestEachServiceDto;
import com.lge.cto.swinfra.tiger.util.DateUtil;
import com.lge.cto.swinfra.tiger.vo.AvgRestofUriVO;

/**
 * 기간 지연 관련 서비스
 * @author suninno.kim@lge.com
 * @date 2014. 5. 16.
 */
@Service
@Transactional(readOnly = true)
public class LatencyService extends ServiceRoot {

    @Autowired
    private LatencyDao	latencyDao;

    private static final Logger logger = LoggerFactory.getLogger(LatencyService.class);

    
    /**
     * 각 서비스에 있어서 기간별 지연 통계 정보<br/>
     * - 검색 기간이 1일 이하 일 경우, 분단위로 집계
     * - 30일 이하일 경우, 시간 단위로 집계
     * - 30일을 넘길 경우, 일 단위로 집계
     * @param sdatetime	검색 시작 일시 (주의, 분 단위. 예, yyyy-mm-dd HH:MI)
     * @param edatetime	검색 종료 일시 (주의, 분 단위. 예, yyyy-mm-dd HH:MI)
     * @param logType	logType
     * @param service	각 서비스 명칭
     * @return	list of AvgRestEachServiceDto
     * @throws ParseException
     */
    public List<AvgRestEachServiceDto> retrieveLatencyForPeriod(
    		String sdatetime,
    		String edatetime,
    		String logType,
    		String service
    		) throws ParseException{
    	
    	DateUtil.isValidDateParameters(sdatetime, DateUtil.YMDHM_FORMAT);
    	DateUtil.isValidDateParameters(edatetime, DateUtil.YMDHM_FORMAT);
    	
    	Map<String, String> params = newHashMap();
    	params.put("sdatetime", sdatetime);
    	params.put("edatetime", edatetime);
    	params.put("logType", logType);
        if (service.compareToIgnoreCase("all") != 0)
            params.put("service", service);
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	
    	Date d1 = format.parse(sdatetime);
    	Date d2 = format.parse(edatetime);
    	
    	long diff = d2.getTime() - d1.getTime();
    	float diffDays = (float)diff / (24 * 60 * 60 * 1000);
    	
    	
    	Map<String, AvgRestEachServiceDto> res = new TreeMap<String, AvgRestEachServiceDto>();
    	List<AvgRestofUriVO> list;
    	
    	
    	if (diffDays > 30) {
    		//검색 기간이 1달 초과하면, 일 단위로 집계한다.
    		list = latencyDao.retrieveLatencyPerDay(params);
    	} else if (diffDays > 1.0) {
    		//검색 기간이 1일을 초과하면, 시간 단위로 집계한다.
    		list = latencyDao.retrieveLatencyperHour(params);

    	} else {
    		//검색 기간이 1일 이하며, 분 단위로 집계한다.
    		list = latencyDao.retrieveLatencyperMin(params);
    	}
    	
    	
    	for(AvgRestofUriVO e : list) {
    		
    		AvgRestEachServiceDto dto = res.get(e.getCalledTime());
    		if (dto == null){
    			res.put(e.getCalledTime(), (dto = new AvgRestEachServiceDto(e.getTime4csv(), e.getCalledTime())));
    		}
    		
    		switch(e.getService().toLowerCase()){
    		
    		case ServiceName.COLLAB1:
    			dto.setCollab1(e);
    			break;
    			
    		case ServiceName.CROWD:
    			dto.setCrowd(e);
    			break;
    		
    		case ServiceName.JIRA1:
    			dto.setJira1(e);
    			break;
    			
    		case ServiceName.JIRA2:
    			dto.setJira2(e);
    			break;
    			
    		case ServiceName.JIRA3:
    			dto.setJira3(e);
				break;
				
    		case ServiceName.JIRA4:
    			dto.setJira4(e);
				break;
				
    		case ServiceName.JIRA5:
    			dto.setJira5(e);
				break;
				
    		case ServiceName.JIRA6:
    			dto.setJira6(e);
				break;
				
    		case ServiceName.JIRA7:
    			dto.setJira7(e);
				break;

    		case ServiceName.JIRA_TV:
    			dto.setJiraTv(e);
				break;
				
    		case ServiceName.JIRA_HARMONY:
    			dto.setJiraHarmony(e);
				break;

			default:
	              String msg = "Unknow Service (" + e.getService() + ")";
	              logger.error(msg);
	              throw new RuntimeException(msg);
    		}
    	}
    	
        return new ArrayList<AvgRestEachServiceDto>(res.values());
    }
}
