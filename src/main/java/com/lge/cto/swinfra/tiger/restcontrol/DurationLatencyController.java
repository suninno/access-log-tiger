package com.lge.cto.swinfra.tiger.restcontrol;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lge.cto.swinfra.tiger.domain.AvgRestEachServiceDto;
import com.lge.cto.swinfra.tiger.service.LatencyService;

/**
 * 서비스별 기간 지연 restful API
 * @author suninno.kim@lge.com
 * @date 2014. 5. 16.
 */
@Controller
@RequestMapping("rest/durlatency")
public class DurationLatencyController {

    @Autowired
    LatencyService	latencyService;

    /**
     * 기간 지연 검색을 위한 REST API<br />
     * 해당정보를 JSON 형식으로 반환
     * @param sdatetime	검색 시작 일시(주의, 분단위. 예, yyyy-mm-dd HH:MI)
     * @param edatetime	검색 종료 일시(주의, 분단위. 예, yyyy-mm-dd HH:MI)
     * @param logType	logtype
     * @param service	각 서비스 명칭
     * @param model
     * @param locale
     * @return	
     * @throws ParseException
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET,
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
    

    /**
     * 기간 지연 검색을 위한 REST API<br />
     * 해당정보를 CSV 형식으로 반환
     * @param sdatetime	검색 시작 일시(주의, 분단위. 예, yyyy-mm-dd HH:MI)
     * @param edatetime	검색 종료 일시(주의, 분단위. 예, yyyy-mm-dd HH:MI)
     * @param logType	logtype
     * @param service	각 서비스 명칭
     * @param response
     * @param model
     * @param locale
     * @throws ParseException
     * @throws IOException
     */
    @RequestMapping(value="/search.csv", method = RequestMethod.GET,
    		produces = {})
    @ResponseStatus(HttpStatus.OK)
    public void retrieveDurationLatency4CSV(
    		@RequestParam String sdatetime,
            @RequestParam String edatetime,
            @RequestParam String logType,
    		@RequestParam String service,
            HttpServletResponse response,
            ModelMap model,
            Locale locale
    		) throws ParseException, IOException{
    	
    	List<AvgRestEachServiceDto> list = latencyService.retrieveLatencyForPeriod(sdatetime, edatetime, logType, service);
    	
        response.setContentType("text/plain; charset=utf-8"); //text/csv일 경우, file download 되어버림.
        response.getWriter().println(AvgRestEachServiceDto.getCSVHeader());
        for (AvgRestEachServiceDto dto : list) {
            response.getWriter().println(dto.getCSVValue());
        }
    }
    
}