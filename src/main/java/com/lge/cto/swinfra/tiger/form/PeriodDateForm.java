package com.lge.cto.swinfra.tiger.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class PeriodDateForm {
    /**
     * 검색 일자(yyyy-mm-dd)
     */
    @NotEmpty(message = "검색할 일자는 필수입니다！.")
    @Pattern(
            regexp = "^[\\d]{4}-[\\d]{2}-[\\d]{2}$",
            message = "검색 시작 일자（yyyy-mm-dd）를 입력하세요!.")
    protected String sdate;

    @NotEmpty(message = "검색할 일자는 필수입니다！.")
    @Pattern(
            regexp = "^[\\d]{4}-[\\d]{2}-[\\d]{2}$",
            message = "검색 종료 일자（yyyy-mm-dd）를 입력하세요!.")
    protected String edate;

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " - sdate: " + sdate
                + " , edate: " + edate;
    }
}
