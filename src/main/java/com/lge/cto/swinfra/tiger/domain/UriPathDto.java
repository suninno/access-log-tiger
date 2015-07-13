package com.lge.cto.swinfra.tiger.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * URI Path에 관한 통계 DTO
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@XmlRootElement
public class UriPathDto {

    /** URI부에서의 path */
    private String uriPath;

    /** 호출된 횟수 */
    private long hitCount;

    /**
     * 평균 지연 [단위㎲]
     */
    private long avgElapTime;

    /**
     * 최소 지연 [단위㎲]
     */
    private long minElapTime;

    /**
     * 최대 지연 [단위㎲]
     */
    private long maxElapTime;

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public long getHitCount() {
        return hitCount;
    }

    public void setHitCount(long hitCount) {
        this.hitCount = hitCount;
    }

    public long getAvgElapTime() {
        return avgElapTime;
    }

    public void setAvgElapTime(long avgElapTime) {
        this.avgElapTime = avgElapTime;
    }

    public long getMinElapTime() {
        return minElapTime;
    }

    public void setMinElapTime(long minElapTime) {
        this.minElapTime = minElapTime;
    }

    public long getMaxElapTime() {
        return maxElapTime;
    }

    public void setMaxElapTime(long maxElapTime) {
        this.maxElapTime = maxElapTime;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " - uriPath: " + this.uriPath +
                ", hitCount: " + this.hitCount +
                ", avgElapTime: " + this.avgElapTime +
                ", minElapTime: " + this.minElapTime +
                ", maxElapTime: " + this.maxElapTime;

    }

}
