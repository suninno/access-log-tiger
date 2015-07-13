package com.lge.cto.swinfra.tiger.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * IP 대역별 평균 지연 DTO
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@XmlRootElement
public class AvgLatencyOfIPRangeDto {

    /**
     * 시간대 (0~23)
     */
    private String hh;

    /**
     * MC A 지연[단위㎲] 
     */
    double mcaLatency;

    /**
     * MC C 지연[단위㎲]
     */
    double mccLatency;

    /**
     * 그 외 지연[단위㎲]
     */
    double othersLatency;

    public AvgLatencyOfIPRangeDto(String hh) {
        this.hh = hh;
    }

    public AvgLatencyOfIPRangeDto(String hh, double mca, double mcc, double others) {
        this.hh = hh;
        this.mcaLatency = mca;
        this.mccLatency = mcc;
        this.othersLatency = others;
    }

    public String getHh() {
        return hh;
    }

    public void setHh(String hh) {
        this.hh = hh;
    }

    public double getMcaLatency() {
        return mcaLatency;
    }

    public void setMcaLatency(double mcaLatency) {
        this.mcaLatency = mcaLatency;
    }

    public double getMccLatency() {
        return mccLatency;
    }

    public void setMccLatency(double mccLatency) {
        this.mccLatency = mccLatency;
    }

    public double getOthersLatency() {
        return othersLatency;
    }

    public void setOthersLatency(double othersLatency) {
        this.othersLatency = othersLatency;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " - hh: " + hh
                + ", mcALatency: " + mcaLatency
                + ", mcCLatency: " + mccLatency
                + ", othersLatency: " + othersLatency;
    }

}
