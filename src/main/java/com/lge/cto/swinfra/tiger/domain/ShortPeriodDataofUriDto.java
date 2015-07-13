package com.lge.cto.swinfra.tiger.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 특정 기간 내의 상세 정보 DTO (특정 URI Path에 있어서)
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@XmlRootElement
public class ShortPeriodDataofUriDto {
	
    /**
     * 호출된 시각(분단위?)
     */
    private String time;

    /**
     * URI Path
     */
    private String uripath;


    /**
     * 호출 횟수
     */
    private int rowcnt;
    
    /**
     * 지연 총계
     */
    private long sumlatency;
    
    /**
     * 평균 지연
     */
    private double avgLatency;

    


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUripath() {
		return uripath;
	}

	public void setUripath(String uripath) {
		this.uripath = uripath;
	}

	public int getRowcnt() {
		return rowcnt;
	}

	public void setRowcnt(int rowcnt) {
		this.rowcnt = rowcnt;
	}

	public long getSumlatency() {
		return sumlatency;
	}

	public void setSumlatency(long sumlatency) {
		this.sumlatency = sumlatency;
	}

	public double getAvgLatency() {
		return avgLatency;
	}

	public void setAvgLatency(double avgLatency) {
		this.avgLatency = avgLatency;
	}

}
