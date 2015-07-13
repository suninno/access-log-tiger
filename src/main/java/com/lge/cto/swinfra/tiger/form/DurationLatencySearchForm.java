package com.lge.cto.swinfra.tiger.form;


/**
 * 기간 지연 검색 폼
 * 
 * @author suninno.kim@lge.com
 * @date 2014. 5. 16
 */
public class DurationLatencySearchForm extends PeriodDatetimeForm {


    /**
     * apache or tomcat
     */
    private String logType;

    /**
     * 각 서비스 (e.g., jira1,..., collab1)
     */
    private String service;
	
    
    public DurationLatencySearchForm() {
        this.logType = "tomcat";
        this.service = "all";
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
		return this.getClass().getName() + " [logType=" + logType + ", service="
				+ service + ", sdatetime=" + super.sdatetime + ", edatetime="
				+ super.edatetime + "]";
	}
    
}
