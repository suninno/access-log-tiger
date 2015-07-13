package com.lge.cto.swinfra.tiger.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 검색기간 yyyy-mm-dd hh:MM
 * 초 단위가 없다는 점 유념
 * @author suninno.kim
 * @date 2014. 4. 10.
 */
public class PeriodDatetimeForm {

    @NotEmpty(message = "검색 시작 일시는 필수입니다！.")
	@Pattern(
			regexp = "^[\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}$",
			message = "검색할 일자（yyyy-mm-dd HH:MI）를 입력하세요!.")
	protected String sdatetime;

	@NotEmpty(message = "검색 종료 일자는 필수입니다！.")
	@Pattern(
			regexp = "^[\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}$",
			message = "검색할 일자（yyyy-mm-dd HH:MI）를 입력하세요!.")
	protected String edatetime;

    
    public String getSdatetime() {
        return sdatetime;
    }

    public void setSdatetime(String sdatetime) {
        this.sdatetime = sdatetime;
    }

    public String getEdatetime() {
        return edatetime;
    }

    public void setEdatetime(String edatetime) {
        this.edatetime = edatetime;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " - sdatetime: " + sdatetime
                + " , edatetime: " + edatetime;
    }
}
