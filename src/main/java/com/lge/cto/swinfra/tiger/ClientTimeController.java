package com.lge.cto.swinfra.tiger;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lge.cto.swinfra.tiger.form.LatencyPerIPSearchForm;
import com.lge.cto.swinfra.tiger.form.PeriodDateForm;

/**
 * ClientTime 관련 Controller
 * @author suninno.kim
 * @date 2014. 4. 10.
 */
@Controller
@RequestMapping("clienttime")
public class ClientTimeController extends AbstractController {

    /**
     * client time 시간별 통계 - 초기화면
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String clienttimeHome(ModelMap model, Locale locale) {
        model.addAttribute("serverTime", super.getServerTime(locale));
        model.addAttribute("latencyPerIPSearchForm", new LatencyPerIPSearchForm());
        return "clienttimeHourly";
    }

    /**
     * client time 시간별 통계 - 검색
     * @param latencyPerIPSearchForm
     * @param result
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String clienttimeSearch(
            @Valid LatencyPerIPSearchForm latencyPerIPSearchForm,
            BindingResult result,
            ModelMap model,
            Locale locale) {

        model.addAttribute("serverTime", super.getServerTime(locale));
        if (result.hasErrors()) {
            model.addAttribute("error", true);
            return "clienttimeHourly";
        }

        return "clienttimeHourly";

    }

    /**
     * client time 일별 통계 - 초기화면
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/daily/", method = RequestMethod.GET)
    public String clienttimeDailyHome(ModelMap model, Locale locale) {

        model.addAttribute("serverTime", super.getServerTime(locale));
        model.addAttribute("periodDateForm", new PeriodDateForm());
        return "clienttimeDaily";
    }

    /**
     * client time 일별 통계 - 검색
     * @param model
     * @param locale
     * @return
     */
    @RequestMapping(value = "/daily/", method = RequestMethod.POST)
    public String clienttimeDailySearch(
            @Valid PeriodDateForm periodDateForm,
            BindingResult result,
            ModelMap model, Locale locale) {

        model.addAttribute("serverTime", super.getServerTime(locale));

        if (result.hasErrors()) {
            model.addAttribute("error", true);
        }
        return "clienttimeDaily";
    }
}
