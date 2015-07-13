package com.lge.cto.swinfra.tiger.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * URI 이력 비교 페이지 - 검색 폼
 * 
 * @author suninno.kim
 * @date 2014. 7. 1.
 */
public class UriDetailHistoryForm { 

	/**
	 * 검색 일자(yyyy-mm-dd)
	 */
	@NotEmpty(message = "검색할 일자는 필수입니다！.")
	@Pattern(regexp = "^[\\d]{4}-[\\d]{2}-[\\d]{2}$",
			message = "검색할 일자（yyyy-mm-dd）를 입력하세요!.")
	private String searchdate;

    	
    @NotEmpty(message = "검색할 URI는 필수입니다！.")
    private String uripath;

    
	@NotEmpty(message = "검색 대상 Log를 선택하세요!.")
	private String logType;
	
	@NotEmpty(message = "검색 대상 service를 선택하세요!.")
	private String service;
    
	
	public UriDetailHistoryForm(){
        this.logType = "tomcat";
        this.service = "all";
	}
    

    public String getSearchdate() {
		return searchdate;
	}

	public void setSearchdate(String searchdate) {
		this.searchdate = searchdate;
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
		return this.getClass().getName() + " [searchdate=" + searchdate
				+ ", uripath=" + uripath 
				+ ", logTYpe=" + logType
				+ ", service=" + service
				+ "]";
	}

}
