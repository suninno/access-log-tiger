package com.lge.cto.swinfra.tiger;

import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lge.cto.swinfra.tiger.form.DailySearchForm;
import com.lge.cto.swinfra.tiger.form.DurationLatencySearchForm;
import com.lge.cto.swinfra.tiger.form.LatencyPerIPSearchForm;
import com.lge.cto.swinfra.tiger.service.StatusService;
import com.lge.cto.swinfra.tiger.service.UriService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UriService uriService;
    
    @Autowired
    StatusService statusService;

    /**
     * Index 페이지
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);

        model.addAttribute("serverTime", super.getServerTime(locale));
        return "home";
    }

    /**
     * 실시간 모니터링 - 초기화면
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/real/", method = RequestMethod.GET)
    public String displayReal(ModelMap model, Locale locale) {
        model.addAttribute("serverTime", super.getServerTime(locale));

        return "realtime";
    }

    /**
     * 일자별 통계 페이지 - 초기화면
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/daily/", method = RequestMethod.GET)
    public String displayDaily(ModelMap model, Locale locale) {
        model.addAttribute("serverTime", super.getServerTime(locale));
        model.addAttribute("dailySearchForm", new DailySearchForm());

        model.addAttribute("logTypeList", getLogTypeList());
        model.addAttribute("serviceList", getTomcatServiceList());

        return "OverviewofURIDaily";
    }

    /**
     * 일자별 통계 페이지 - 검색
     * @param dailySearchForm
     * @param result
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/daily/", method = RequestMethod.POST)
    public String searchDaily(@Valid DailySearchForm dailySearchForm,
            BindingResult result, ModelMap model, Locale locale) {

        model.addAttribute("serverTime", super.getServerTime(locale));
        model.addAttribute("logTypeList", getLogTypeList());
        model.addAttribute("serviceList", getTomcatServiceList());

        if (result.hasErrors()) {
            model.addAttribute("error", true);
            return "OverviewofURIDaily";
        }

        return "OverviewofURIDaily";
    }

    /**
     * 지연별 통게 - 초기화면
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/latency/", method = RequestMethod.GET)
    public String displayLatency(ModelMap model, Locale locale) {
        model.addAttribute("serverTime", super.getServerTime(locale));

        return "latency";
    }

    /**
     * 기간 지연 - 초기화면
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/dur-latency/", method = RequestMethod.GET)
    public String displayDurLatency(ModelMap model, Locale locale) {
    	model.addAttribute("serverTime", super.getServerTime(locale));
        model.addAttribute("durationLatencySearchForm", new DurationLatencySearchForm());
        model.addAttribute("logTypeList", getLogTypeList());
        model.addAttribute("serviceList", getTomcatServiceList());

    	return "durLatency";
    }
    
    /**
     * 기간 지연 - 검색화면
     * @param durationLatencySearchForm
     * @param result
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/dur-latency/", method = RequestMethod.POST)
    public String clienttimeDailySearch(
            @Valid DurationLatencySearchForm durationLatencySearchForm,
            BindingResult result,
            ModelMap model, Locale locale) {

        model.addAttribute("serverTime", super.getServerTime(locale));
        model.addAttribute("logTypeList", getLogTypeList());
        model.addAttribute("serviceList", getTomcatServiceList());
        
        if (durationLatencySearchForm.getEdatetime().compareToIgnoreCase(durationLatencySearchForm.getSdatetime()) < 0) {
        	model.addAttribute("errorMsg", "검색 시작일자는 종료일자보다 작아야 합니다.");
        	result.addError(new ObjectError("sdatetime", "검색 시작일자는 종료일자보다 이전이어야 합니다."));
        }

        if (result.hasErrors()) {
            model.addAttribute("error", true);
        }
        
        return "durLatency";
    }
    		

    
    /**
     * Source IP별 검색 페이지 - 초기화면
     * SRS: 
     * 	1. 각 대역별로 latency 차이
     * 	2. MC A, MC, 평균 간의 차이
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/srcip/", method = RequestMethod.GET)
    public String srcipHome(ModelMap model, Locale locale) {
        model.addAttribute("serverTime", super.getServerTime(locale));
        model.addAttribute("latencyPerIPSearchForm", new LatencyPerIPSearchForm());
        model.addAttribute("logTypeList", getLogTypeList());
        return "srcip";
    }

    /**
     * Source IP별 검색 페이지 - 검색
     * @param latencyPerIPSearchForm
     * @param result
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/srcip/", method = RequestMethod.POST)
    public String srcipSearch(
            @Valid LatencyPerIPSearchForm latencyPerIPSearchForm,
            BindingResult result,
            ModelMap model, Locale locale) {

        model.addAttribute("serverTime", super.getServerTime(locale));
        model.addAttribute("logTypeList", getLogTypeList());

        if (result.hasErrors()) {
            model.addAttribute("error", true);
            return "srcip";
        }

        return "srcip";
    }
    
    
    @RequestMapping(value="/status/data/", method = RequestMethod.GET)
    public String status_data(ModelMap model, Locale locale) {
        model.addAttribute("serverTime", super.getServerTime(locale));
        model.addAttribute("list", statusService.retrieveStatusData() );
        return "statusData";
    }

}
