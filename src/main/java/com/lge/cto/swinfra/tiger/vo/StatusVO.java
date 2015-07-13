package com.lge.cto.swinfra.tiger.vo;

import java.io.Serializable;

/**
 * Client time의 일별 평균 지연 Value Object
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
public class StatusVO implements Serializable {

	private static final long serialVersionUID = 3086975374085678695L;


    /**
     * 일자(yyyy-mm-dd)
     */
    private String ymd;

    /**
     * 서비스 명(collab1, jira3)
     */
    private String service;


    /**
     * 데이터 수 
     */
    private long cnt;


	public String getYmd() {
		return ymd;
	}


	public void setYmd(String ymd) {
		this.ymd = ymd;
	}


	public String getService() {
		return service;
	}


	public void setService(String service) {
		this.service = service;
	}


	public long getCnt() {
		return cnt;
	}


	public void setCnt(long cnt) {
		this.cnt = cnt;
	}


	@Override
	public String toString() {
		return this.getClass().getName()  + "[ymd=" + ymd + ", service=" + service
				+ ", cnt=" + cnt + "]";
	}
    
    
    

}
