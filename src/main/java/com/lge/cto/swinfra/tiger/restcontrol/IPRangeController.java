package com.lge.cto.swinfra.tiger.restcontrol;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lge.cto.swinfra.tiger.domain.AvgLatencyOfIPRangeDto;
import com.lge.cto.swinfra.tiger.domain.HttpStatusOfIPRangeDto;
import com.lge.cto.swinfra.tiger.service.IPRangeService;

/**
 * IP 대역별 통계 관련 restful API
 * @author suninno.kim@lge.com
 * @date 2014.04.04
 */
@Controller
@RequestMapping("rest/srcip")
public class IPRangeController {

    @Autowired
    IPRangeService iprangeService;

    /**
     * IP 대역별 Http Status 통계
     * service: jira3, collab1은 하드코딩
     * @param date	검색 일자
     * @param logType	apache or tomcat
     * @param model
     * @param local
     * @return
     */
    @RequestMapping(value = "/httpstatus", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<HttpStatusOfIPRangeDto> retrieveHttpStatusOfIPRange(
            @RequestParam String date,
            @RequestParam String logType,
            ModelMap model,
            Locale local
            ) {
        return iprangeService.retrieveHttpStatusOfIPRange(date, logType);
    }

    /**
     * IP 대역별 시간별 평균 지연
     * @param date	검색 일자
     * @param logType	apache or tomcat
     * @param service	jira3 or collab1
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/hourly", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<AvgLatencyOfIPRangeDto> retrieveAvgRestofIPRange(
            @RequestParam String date,
            @RequestParam String logType,
            @RequestParam String service,
            ModelMap model,
            Locale locale
            ) {
        return iprangeService.retrieveAvgRestofIPRange(date, logType, service);
    }
}
