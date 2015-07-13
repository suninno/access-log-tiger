package com.lge.cto.swinfra.tiger.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 기간별 특정 URI Path에 대한 평균 지연 페이지 - 검색 폼
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
public class UriDetailSearchForm {

	/**
	 * 검색 일자(yyyy-mm-dd)
	 */
	@NotEmpty(message = "검색할 일자는 필수입니다！.")
	@Pattern(
			regexp = "^[\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}$",
			message = "검색할 일자（yyyy-mm-dd HH:MI）를 입력하세요!.")
	private String sdatetime;

	@NotEmpty(message = "검색할 일자는 필수입니다！.")
	@Pattern(
			regexp = "^[\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}$",
			message = "검색할 일자（yyyy-mm-dd HH:MI）를 입력하세요!.")
	private String edatetime;

	@NotEmpty(message = "검색할 URI는 필수입니다！.")
	private String uripath;
	
	
	@NotEmpty(message = "검색 대상 Log를 선택하세요!.")
	private String logType;
	
	@NotEmpty(message = "검색 대상 service를 선택하세요!.")
	private String service;
	
	public UriDetailSearchForm(){
        this.logType = "tomcat";
        this.service = "all";
	}

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

	public String getUripath() {
		return uripath;
	}

	public void setUripath(String uripath) {
		this.uripath = uripath;
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
		return this.getClass().getName() + " [sdatetime=" + sdatetime
				+ ", edatetime=" + edatetime + ", uripath=" + uripath
				+ ", logType=" + logType + ", service=" + service + "]";
	}

}
