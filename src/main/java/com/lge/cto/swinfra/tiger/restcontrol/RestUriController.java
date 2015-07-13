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

import com.lge.cto.swinfra.tiger.domain.AvgRestofUriDto;
import com.lge.cto.swinfra.tiger.domain.ShortPeriodDataofUriDto;
import com.lge.cto.swinfra.tiger.domain.UriHistoryDto;
import com.lge.cto.swinfra.tiger.service.UriService;

/**
 * 일반적인 restful API
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@Controller
@RequestMapping("rest/uri")
public class RestUriController {

    @Autowired
    UriService uriService;

    /**
     * REST API for 일자별 특정 URIPATH에 대한 평균 지연 그래프
     * @param date		검색 일자
     * @param uripath	검색 uripath
     * @param model
     * @param locale
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value = "/avgRest", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<AvgRestofUriDto> retrieveAvgRestofUri(
            @RequestParam String date,
            @RequestParam(value="uripath", required=false) String uripath,
            @RequestParam String logType,
            @RequestParam String service,
            ModelMap model, Locale locale) throws ParseException {

        return uriService.retrieveAvgRestofUri(date, uripath, logType, service);
    }

    /**
     * 특정 기간 동안의 임의 URI Path에 대한 평균 지연과 호출 횟수
     * @NOTE: 현재 사용되지 않음
     * @param sdatetime	검색 시작 일시
     * @param edatetime	검색 종료 일시
     * @param uripath	검색 대상 uri path
     * @param logType	apache or tomcat
     * @param service	jira1,..,collab1
     * @param model
     * @param locale
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/averageLatencyForPeriod", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<ShortPeriodDataofUriDto> retrieveShotPeriodDataofUri(
            @RequestParam String sdatetime,
            @RequestParam String edatetime,
            @RequestParam String uripath,
            @RequestParam String logType,
            @RequestParam String service,
            ModelMap model, Locale locale) throws ParseException {

    	return uriService.retrieveShortPeriodDataofUri(sdatetime, edatetime, uripath, logType, service);
    }

    
    /**
     * URI 이력 비교
     * @param searchdate	검색 대상 일자
     * @param uripath		uri
     * @param logType		logType
     * @param service		service name
     * @param model
     * @param locale
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/urihistory", method = RequestMethod.GET,
    		produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<UriHistoryDto> retrieveUriHistory(
    		@RequestParam String searchdate,
    		@RequestParam String uripath,
    		@RequestParam String logType,
    		@RequestParam String service,
    		ModelMap model, Locale locale) throws ParseException {
    	
    	return uriService.retrieveUriHistory(searchdate, uripath, logType, service);
    	
    }
    
}