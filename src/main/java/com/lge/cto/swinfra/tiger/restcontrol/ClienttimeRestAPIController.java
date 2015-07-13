package com.lge.cto.swinfra.tiger.restcontrol;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lge.cto.swinfra.tiger.domain.AvgClienttimeDailyDto;
import com.lge.cto.swinfra.tiger.domain.AvgClienttimeHourlyDto;
import com.lge.cto.swinfra.tiger.service.ClienttimeService;

/**
 * IP 대역별 ClientTime 통계 관련 restful API
 * @author suninno.kim@lge.com
 * @date 2014.04.04
 */
@Controller
@RequestMapping("rest/clienttime")
public class ClienttimeRestAPIController {

    @Autowired
    ClienttimeService clienttimeService;

    /**
     * IP 대역별 시간별 ClientTime 평균 지연 데이터를 JSON 형식으로 반환한다.
     * @param date	검색 일자
     * @param service	jira3 or collab1
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/hourly/search", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<AvgClienttimeHourlyDto> retrieveAvgRestofIPRange(
            @RequestParam String date,
            @RequestParam(required = false) String service,
            ModelMap model,
            Locale locale
            ) {
        return clienttimeService.retrieveAvgLatencyHourlyClienttime(date, service);
    }

    /**
     * IP 대역별 시간별 ClientTime 평균 지연 데이터를 CSV 형식으로 반환한다.
     * @param date	검색 일자
     * @param service	서비스명(jira3, collab1 or null) 
     * @param response
     * @param model
     * @param locale
     * @throws IOException
     */
    @RequestMapping(value = "/hourly/search.csv", method = RequestMethod.GET,
            produces = { "text/html", "application/vnd.ms-excel", "text/csv" })
    @ResponseStatus(HttpStatus.OK)
    public void retrieveAvgRestofIPRange4CSV(
            @RequestParam String date,
            @RequestParam(required = false) String service,
            HttpServletResponse response,
            ModelMap model,
            Locale locale
            ) throws IOException {
        List<AvgClienttimeHourlyDto> list = clienttimeService.retrieveAvgLatencyHourlyClienttime(
                date, service);

        response.setContentType("text/plain; charset=utf-8"); //text/csv일 경우, file download 되어버림.
        response.getWriter().println("일자: " + date);
        response.getWriter().println(AvgClienttimeHourlyDto.getCSVHeader());
        for (AvgClienttimeHourlyDto dto : list) {
            response.getWriter().println(dto.getCSVValue());
        }
    }

    /**
     * IP 대역별 일별 ClientTime 평균 지연 데이터를 JSON 형식으로 반환한다.
     * @param sdate		검색 시작 일자(폐구간)
     * @param edate		검색 종료 일자(페구간)
     * @param service	서비스명(jira3, collab1 or null)
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/daily/search", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<AvgClienttimeDailyDto> retrieveAvgRsDailyClienttime(
            @RequestParam String sdate,
            @RequestParam String edate,
            @RequestParam(required = false) String service,
            ModelMap model,
            Locale locale
            ) {

        return clienttimeService.retrieveAvgRsDailyClienttime(sdate, edate, service);
    }

    /**
     * IP 대역별 일별 ClientTime 평균 지연 데이터를 CSV 형식으로 반환한다.
     * @param sdate		검색 시작 일자(폐구간)
     * @param edate		검색 종료 일자(페구간)
     * @param service	서비스명(jira3, collab1 or null)
     * @param model
     * @param response
     * @param locale
     * @throws IOException
     * @throws JSONException
     */
    @RequestMapping(value = "/daily/search.csv", method = RequestMethod.GET,
            produces = { "text/html", "application/vnd.ms-excel", "text/csv" })
    @ResponseStatus(HttpStatus.OK)
    public void retrieveAvgRsDailyClienttime4CSV(
            @RequestParam String sdate,
            @RequestParam String edate,
            @RequestParam(required = false) String service,
            ModelMap model,
            HttpServletResponse response,
            Locale locale
            ) throws IOException, JSONException {

        List<AvgClienttimeDailyDto> list = clienttimeService.retrieveAvgRsDailyClienttime(sdate,
                edate, service);

        response.setContentType("text/plain; charset=utf-8"); //text/csv일 경우, file download 되어버림.
        response.getWriter().print(AvgClienttimeDailyDto.getCSVHeader() + "\n");
        for (AvgClienttimeDailyDto dto : list) {
            response.getWriter().print(dto.getCSVValue() + "\n");
        }
    }

}
