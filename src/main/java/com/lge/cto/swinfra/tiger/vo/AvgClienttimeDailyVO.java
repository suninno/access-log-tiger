package com.lge.cto.swinfra.tiger.vo;

import java.io.Serializable;

/**
 * Client time의 일별 평균 지연 Value Object
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
public class AvgClienttimeDailyVO implements Serializable {

    private static final long serialVersionUID = 5128044687839675236L;

    /**
     * 일자(yyyy-mm-dd)
     */
    private String ymd;

    /**
     * 서비스 명(collab1, jira3)
     */
    private String service;

    /**
     * IP 대역(mc a, mc c, others)
     */
    private String ip_subset;

    /**
     * 평균 Rs[단위㎲]
     */
    private double avgRs;

    /**
     * 평균 Re[단위㎲]
     */
    private double avgRe;

    /**
     * 평균 Lee[단위㎲]
     */
    private double avgLee;

    /**
     * 이 Reference의 키 값을 취한다.
     * @return
     */
    public final String getKey() {
        return ymd + "_" + service;
    }

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

    public String getIp_subset() {
        return ip_subset;
    }

    public void setIp_subset(String ip_subset) {
        this.ip_subset = ip_subset;
    }

    public double getAvgRs() {
        return avgRs;
    }

    public void setAvgRs(double avgRs) {
        this.avgRs = avgRs;
    }

    public double getAvgRe() {
        return avgRe;
    }

    public void setAvgRe(double avgRe) {
        this.avgRe = avgRe;
    }

    public double getAvgLee() {
        return avgLee;
    }

    public void setAvgLee(double avgLee) {
        this.avgLee = avgLee;
    }

    @Override
    public String toString() {
        return "AvgClienttimeDailyVO [ymd=" + ymd + ", service=" + service
                + ", ip_subset=" + ip_subset + ", AvgRs=" + avgRs + ", AvgRe="
                + avgRe + ", AvgLee=" + avgLee + ", getClass()=" + getClass()
                + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }

}
