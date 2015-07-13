package com.lge.cto.swinfra.tiger.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 서비스별 평균 지연과 호출 횟수 Value Object
 * 
 * @author suninno.kim
 * @date 2014. 7. 5.
 */
@XmlRootElement
public class AvgRestofUriVO  implements Serializable {
	
	private static final long serialVersionUID = 2283796707805359024L;

	/**
	 * csv 출력을 위한 시각
	 */
	private String time4csv;
	
    /**
     * 시각
     */
    private String calledTime;

    
    /**
     * 서비스 명(collab1, jira3)
     */
    private String service;
    
    /**
     * 평균 지연 [단위㎲]
     */
    private double avgRest;

    /**
     * 횟수
     */
    private int count;

    
    public String getTime4csv() { 
    	return time4csv;
    }
    
    public void setTime4csv(String time4csv){
    	this.time4csv	= time4csv;
    }
    
    public String getCalledTime() {
        return calledTime;
    }
    
    public void setCalledTime(String calledTime) {
        this.calledTime = calledTime;
    }
    
    public String getService(){
    	return service;
    }
    
    public void setService(String service){
    	this.service	= service;
    }

    public double getAvgRest() {
        return avgRest;
    }

    public void setAvgRest(double avgRest) {
        this.avgRest = avgRest;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " - calledTime: " + calledTime
                + " , avgResT: " + avgRest + " , count: " + count;
    }
    
}
