package com.lge.cto.swinfra.tiger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lge.cto.swinfra.tiger.dao.StatusDao;
import com.lge.cto.swinfra.tiger.domain.StatusDataDto;
import com.lge.cto.swinfra.tiger.util.DateUtil;
import com.lge.cto.swinfra.tiger.vo.StatusVO;

/**
 * 기간 지연 관련 서비스
 * @author suninno.kim@lge.com
 * @date 2014. 5. 16.
 */
@Service
@Transactional(readOnly = true)
public class StatusService extends ServiceRoot {
	
    private static final Logger logger = LoggerFactory.getLogger(StatusService.class);

    
    @Autowired
    private StatusDao	statusDao;

    public ArrayList<StatusDataDto> retrieveStatusData() {
    	
    	Map<String, StatusDataDto> res = newTreeMap();
    	
    	//최근 2개월간 ~ 하루 전
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.MONTH, -2);
    	
    	Date begin = cal.getTime();
    	
    	cal = Calendar.getInstance();
    	cal.add(Calendar.DAY_OF_YEAR, -1);
    	Date end = cal.getTime();
    	
    	
    	for(Date i = begin; i.before(end);  ){

    		res.put(DateUtil.getYMD(i), new StatusDataDto(DateUtil.getYMD(i)));
    		
    		Calendar c = Calendar.getInstance();
    		c.setTime(i);
    		c.add(Calendar.DAY_OF_YEAR, 1);
    		i = c.getTime();
    	}
    	
    	
    	List<StatusVO> list = statusDao.retrieveStatuaData();

    	
    	
    	for(StatusVO e : list){
    		
    		StatusDataDto dto = res.get(e.getYmd());
    		if ( dto == null) {
    			//2개월 지난 데이터일 가능성 높음.
    			res.put(e.getYmd(), ( dto = new StatusDataDto(e.getYmd())) );
    		}

    		
    		switch(e.getService().toLowerCase()){
    		case ServiceName.COLLAB1:
    			dto.setCollab1(e.getCnt());
    			break;
    			
    		case ServiceName.CROWD:
    			dto.setCrowd(e.getCnt());
    			break;
    			
    		case ServiceName.JIRA1:
    			dto.setJira1(e.getCnt());
    			break;
    			
    		case ServiceName.JIRA2:
    			dto.setJira2(e.getCnt());
    			break;
    			
    		case ServiceName.JIRA3:
    			dto.setJira3(e.getCnt());
    			break;
    			
    		case ServiceName.JIRA4:
    			dto.setJira4(e.getCnt());
    			break;
    			
    		case ServiceName.JIRA5:
    			dto.setJira5(e.getCnt());
    			break;
    			
    		case ServiceName.JIRA6:
    			dto.setJira6(e.getCnt());
    			break;
    			
    		case ServiceName.JIRA7:
    			dto.setJira7(e.getCnt());
    			break;
    			
    		case ServiceName.JIRA_TV:
    			dto.setJira_tv(e.getCnt());
    			break;
    			
    		case ServiceName.JIRA_HARMONY:
    			dto.setJira_harmony(e.getCnt());
    			break;
    			
			default:
			    String msg = "Unknow Service (" + e.getService() + ")";
			    logger.error(msg);
			    throw new RuntimeException(msg);

    		}
    	}

        return new ArrayList<StatusDataDto>(res.values());
    }

}
