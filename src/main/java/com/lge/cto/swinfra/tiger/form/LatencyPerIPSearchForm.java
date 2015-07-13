package com.lge.cto.swinfra.tiger.form;


import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Source IP 별 통계에서의 검색 폼 service field가 없는 데, 이는 jira3과 collab1 service만 대상으로
 * 하기 때문이다.
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
public class LatencyPerIPSearchForm {

    /**
     * 검색 일자(yyyy-mm-dd)
     */
    @NotEmpty(message = "검색할 일자는 필수입니다！.")
    @Pattern(
            regexp = "^[\\d]{4}-[\\d]{2}-[\\d]{2}$",
            message = "검색할 일자（yyyy-mm-dd）를 입력하세요!.")
    private String searchDate;

    /**
     * apache or tomcat
     */
    private String logType;

    public LatencyPerIPSearchForm() {
        this.logType = "tomcat";
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " - searchDate: " + this.searchDate
                + ", logType: " + logType;
    }
}
