package com.lge.cto.swinfra.tiger.form;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

/**
 * 일자별 통계 보고서에서의 검색 폼
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
public class DailySearchForm {

    /**
     * 검색 일자(yyyy-mm-dd)
     */
    @NotEmpty(message = "검색할 일자는 필수입니다！.")
    @Pattern(
            regexp = "^[\\d]{4}-[\\d]{2}-[\\d]{2}$",
            message = "검색할 일자（yyyy-mm-dd）를 입력하세요!.")
    private String searchDate;

    @DecimalMin(value = "1.00", message = "호출된 최소 횟수(1이상)를 입력하세요!.")
    @Pattern(regexp = "^\\d+", message = "1 이상의 수를 입력하세요!.")
    private String limitCnt;

    /**
     * apache or tomcat
     */
    private String logType;

    /**
     * 각 서비스 (e.g., jira1,..., collab1)
     */
    private String service;

    public DailySearchForm() {
        this.limitCnt = "5";
        this.logType = "tomcat";
        this.service = "all";
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public String getLimitCnt() {
        return limitCnt;
    }

    public void setLimitCnt(String limitCnt) {
        this.limitCnt = limitCnt;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " - searchDate: " + this.searchDate +
                ", limitCnt: " + this.limitCnt +
                ", logType: " + this.logType +
                ", service:" + this.service;
    }
}
