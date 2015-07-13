package com.lge.cto.swinfra.tiger.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * URI path의 평균 지연 DTO
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@XmlRootElement
public class AvgRestofUriDto {

    /**
     * 시각
     */
    private String calledTime;

    /**
     * 평균 지연 [단위㎲]
     */
    private double avgRest;

    /**
     * 횟수
     */
    private int count;

    
    
    
    public String getCalledTime() {
        return calledTime;
    }

    public void setCalledTime(String calledTime) {
        this.calledTime = calledTime;
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
    
    
    /**
     * 이 reference를 CSV 값으로 변활할 때, CSV 데이터의 헤더를 반환한다.
     * @return
     */
    public static final String getCSVHeader() {
        return "시각,횟수,평균지연[㎲]";
    }

    /**
     * Object Value를 CSV형식으로 반환한다.
     * @return
     */
    public String getCSVValue() {
    	return calledTime + "," + count +"," + avgRest;
    }
}
