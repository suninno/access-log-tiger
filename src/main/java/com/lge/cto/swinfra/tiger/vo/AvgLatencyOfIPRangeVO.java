package com.lge.cto.swinfra.tiger.vo;

import java.io.Serializable;

import com.lge.cto.swinfra.tiger.domain.AvgLatencyOfIPRangeDto;

/**
 * IP 대역별 평균 지연 Value Object
 * 
 * @see AvgLatencyOfIPRangeDto
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
public class AvgLatencyOfIPRangeVO implements Serializable {

    private static final long serialVersionUID = -7936436395784823174L;

    /**
     * 시간대 (0~23)
     */
    private String hh;

    /**
     * IP 대역(mc a, mc c, others)
     */
    private String ip_subset;

    /**
     * 평균 지연[단위㎲]
     */
    private double AvgLatency;

    public String getHh() {
        return hh;
    }

    public void setHh(String hh) {
        this.hh = hh;
    }

    public String getIp_subset() {
        return ip_subset;
    }

    public void setIp_subset(String ip_subset) {
        this.ip_subset = ip_subset;
    }

    public double getAvgLatency() {
        return AvgLatency;
    }

    public void setAvgLatency(double avgLatency) {
        AvgLatency = avgLatency;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " [hh=" + hh + ", ip_subset="
                + ip_subset + ", AvgLatency=" + AvgLatency + "]";
    }

}
