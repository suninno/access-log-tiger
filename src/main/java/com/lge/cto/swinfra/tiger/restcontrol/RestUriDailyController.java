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

import com.lge.cto.swinfra.tiger.domain.HttpStatusDto;
import com.lge.cto.swinfra.tiger.domain.LatencyDto;
import com.lge.cto.swinfra.tiger.domain.UriPathDto;
import com.lge.cto.swinfra.tiger.service.UriService;

/**
 * 일자별 REST API 통계 관련 restful API
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@Controller
@RequestMapping("rest/daily")
public class RestUriDailyController {

    @Autowired
    UriService uriService;

    /**
     * 통계표를 위한 REST API
     * @param date	검색 대상 일자 (yyyy-MM-dd)
     * @param limitCnt	대상 일자에 호출된 최소 횟수
     * @param model	
     * @param locale
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/restapisummary", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<UriPathDto> findDataDaily(
            @RequestParam String date,
            @RequestParam String limitCnt,
            @RequestParam String logType,
            @RequestParam String service,
            ModelMap model, Locale locale
            ) throws ParseException {

        return uriService.retrieveRESTApiSummaryDaily(date, limitCnt, logType, service);
    }

    /**
     * 일자별 Http Status 통계
     * @param date	검색 일자
     * @param logType	apache or tomcat
     * @param service	jira1,...., collab1
     * @param model
     * @param locale
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/httpstatus", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<HttpStatusDto> retrieveHttpStatusDaily(
            @RequestParam String date,
            @RequestParam String logType,
            @RequestParam String service,
            ModelMap model, Locale locale) throws ParseException {

        return uriService.retreiveHttpStatusDaily(date, logType, service);
    }

    /**
     * 지연 시간별 통계
     * @param date	검색 일자
     * @param logType	apache or tomcat
     * @param service	jira1,.., collab1
     * @param model
     * @param locale
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/latency", method = RequestMethod.GET
            , produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<LatencyDto> retrieveLatencyDaily(
            @RequestParam String date,
            @RequestParam String logType,
            @RequestParam String service,
            ModelMap model, Locale locale) throws ParseException
    {

        return uriService.retrieveLatencyDaily(date, logType, service);
    }
}