package com.lge.cto.swinfra.tiger;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lge.cto.swinfra.tiger.form.UriDetailHistoryForm;
import com.lge.cto.swinfra.tiger.form.UriDetailSearchForm;
import com.lge.cto.swinfra.tiger.util.DateUtil;


@Controller
@RequestMapping("uridetail")
public class UriDetailController extends AbstractController {

	
    /**
     * 일자별 특정 URI path에 대한 평균 지연 그래프
     * @param date
     * @param uripath
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String viewUriDetail2(
            @RequestParam String date,
            @RequestParam(value="uripath", required=false) String uripath,
            @RequestParam String logType,
            @RequestParam String service,
            ModelMap model, Locale locale) {
        model.addAttribute("serverTime", super.getServerTime(locale));
        model.addAttribute("date", date);
        model.addAttribute("uripath", uripath);
        model.addAttribute("logType", logType);
        model.addAttribute("service", service);

        return "uridetail/UriDetailView";
    }

    /**
     * URI 평균 지연 상세 정보
     * URI 시각 별 평균 지연 그래프의 확대 그래프에서 클릭할 시, 발생하는 이벤트
     * @param datetime	정보 검색을 원하는 시간(주의: 분단위)
     * @param uripath	uri의 path부
     * @param logType	apache or tomcat
     * @param service	jira1,..,collab1
     * @param model
     * @param locale
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/table", method = RequestMethod.GET)
    public String tableUriDetail(
            @RequestParam String datetime,
            @RequestParam String uripath,
            @RequestParam String logType,
            @RequestParam String service,
            ModelMap model, Locale locale) throws ParseException {

        model.addAttribute("serverTime", super.getServerTime(locale));
        model.addAttribute("datetime", datetime);
        model.addAttribute("uripath", uripath);
        model.addAttribute("logType", logType);
        model.addAttribute("service", service);

        Date cur = DateUtil.str2DateTime(datetime + ":00");
        model.addAttribute("sdatetime", DateUtil.getYMDHMS(DateUtil.addMinutes(cur, -30)));
        model.addAttribute("edatetime", DateUtil.getYMDHMS(DateUtil.addMinutes(cur, 30)));

        return "uridetail/UriDetailTable";
    }

    /**
     * 기간별 특정 URI path에 대한 평균 지연 그래프 - 초기 페이지
     * @param uriDetailSearchForm
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/search/", method = RequestMethod.GET)
    public String UriDetailSearchHome(
            ModelMap model, Locale locale) {
    	
        model.addAttribute("serverTime", super.getServerTime(locale));
        model.addAttribute("uriDetailSearchForm", new UriDetailSearchForm());
        model.addAttribute("logTypeList", getLogTypeList());
        model.addAttribute("serviceList", getTomcatServiceList());
        return "uridetail/UriAvgLatency";
    }

    /**
     * 기간별 특정 URI path에 대한 평균 지연 그래프 - 검색 페이지
     * @param uriDetailSearchForm
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/search/", method = RequestMethod.POST)
    public String UriDetailSearch(
            @Valid UriDetailSearchForm uriDetailSearchForm,
            ModelMap model, Locale locale) {
    	
        model.addAttribute("serverTime", super.getServerTime(locale));
        model.addAttribute("logTypeList", getLogTypeList());
        model.addAttribute("serviceList", getTomcatServiceList());
        return "uridetail/UriAvgLatency";
    }


    /**
     * URI 이력 비교 페이지 - 초기화면
     */
    @RequestMapping(value="/history/", method = RequestMethod.GET)
    public String UriDetailHistoryHome(ModelMap model, Locale locale) {
    	model.addAttribute("serverTime", super.getServerTime(locale));
    	model.addAttribute("uriDetailHistoryForm", new UriDetailHistoryForm());
    	
        model.addAttribute("logTypeList", getLogTypeList());
        model.addAttribute("serviceList", getTomcatServiceList());
    	return "uridetail/UriDetailHistory";
    }
    
    /**
     * URI 이력 비교 페이지 - 검색
     */
    @RequestMapping(value="/history/", method = RequestMethod.POST)
    public String UriDetailHistorySearch(
    		@Valid UriDetailHistoryForm uriDetailHistoryForm,
    		BindingResult result,
    		ModelMap model, Locale locale) {
    	
    	model.addAttribute("serverTime", super.getServerTime(locale));
    	
        model.addAttribute("logTypeList", getLogTypeList());
        model.addAttribute("serviceList", getTomcatServiceList());
    	return "uridetail/UriDetailHistory";
    }
    
}
