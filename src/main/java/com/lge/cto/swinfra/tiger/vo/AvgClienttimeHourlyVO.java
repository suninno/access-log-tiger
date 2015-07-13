package com.lge.cto.swinfra.tiger.vo;

import java.io.Serializable;

/**
 * 일별 Client time 평균 지연 Value Object
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
public class AvgClienttimeHourlyVO implements Serializable {

	private static final long serialVersionUID = 4162129407057688254L;

	/**
     * 시간대 (0~23)
     */
    private String hh;

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
     * 평균 Rs[단위㎲]
     */
    private double avgLee;

    public final String getKey() {
        return hh + "_" + service;
    }

    public String getHh() {
        return hh;
    }

    public void setHh(String hh) {
        this.hh = hh;
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
        return "AvgClienttimeHourlyVO [hh=" + hh + ", service=" + service
                + ", ip_subset=" + ip_subset + ", avgRs=" + avgRs + ", avgRe="
                + avgRe + ", avgLee=" + avgLee + "]";
    }

}
