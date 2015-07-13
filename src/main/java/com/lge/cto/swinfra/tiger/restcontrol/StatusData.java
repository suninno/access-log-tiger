package com.lge.cto.swinfra.tiger.restcontrol;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lge.cto.swinfra.tiger.domain.AvgRestEachServiceDto;
import com.lge.cto.swinfra.tiger.service.LatencyService;

/**
 * 데이터 보존 상태 restful API
 * @author suninno.kim@lge.com
 * @date 2014. 5. 16.
 */
@Controller
@RequestMapping("rest/status")
public class StatusData {

    @Autowired
    LatencyService	latencyService;

    /**
     * 기간 지연 검색을 위한 REST API<br />
     * 해당정보를 JSON 형식으로 반환
     * @param service	각 서비스 명칭
     * @param sdatetime	검색 시작 일시(주의, 분단위. 예, yyyy-mm-dd HH:MI)
     * @param edatetime	검색 종료 일시(주의, 분단위. 예, yyyy-mm-dd HH:MI)
     * @param model
     * @param locale
     * @return	
     * @throws ParseException
     */
    @RequestMapping(value = "/data", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<AvgRestEachServiceDto> retrieveDurationLatency(
    		@RequestParam String sdatetime,
            @RequestParam String edatetime,
            @RequestParam String logType,
            @RequestParam String service,
            ModelMap model, Locale locale
            ) throws ParseException {

    	return latencyService.retrieveLatencyForPeriod(sdatetime, edatetime, logType, service);
    }
    
}