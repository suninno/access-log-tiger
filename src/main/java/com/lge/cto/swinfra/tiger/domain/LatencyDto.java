package com.lge.cto.swinfra.tiger.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 지연 시간별 통계 DTO
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@XmlRootElement
public class LatencyDto {

    /**
     * 최소 지연[단위㎲]
     */
    private String threshold_min;
    /**
     * 최대 지연[단위㎲]
     */
    private String threshold_max;

    /**
     * 횟수
     */
    private long count;

    public LatencyDto(String min, String max, long count) {
        this.threshold_min = min;
        this.threshold_max = max;
        this.count = count;
    }

    public String getThreshold_min() {
        return threshold_min;
    }

    public void setThreshold_min(String threshold_min) {
        this.threshold_min = threshold_min;
    }

    public String getThreshold_max() {
        return threshold_max;
    }

    public void setThreshold_max(String threshold_max) {
        this.threshold_max = threshold_max;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " - threshold_min: " + threshold_min
                + ", threshold_max: " + threshold_max + " , count: " + count;
    }
}
