package com.lge.cto.swinfra.tiger.domain;

import javax.xml.bind.annotation.XmlRootElement;

import com.lge.cto.swinfra.tiger.vo.AvgClienttimeDailyVO;

/**
 * <p>Client Time의 일자별 평균 지연 DTO</p>
 * Reference Object의 키값은 ymd_service이다.
 * @author suninno.kim
 * @date 2014. 4. 15.
 */
@XmlRootElement
public class AvgClienttimeDailyDto {

    /**
     * 일자(yyyy-mm-dd)
     */
    private String ymd;

    /**
     * 서비스 명(collab1, jira3)
     */
    private String service;

    /**
     * MC A 평균 Rs[단위㎲]
     */
    private double mcaRs;

    /**
     * MC A 평균 Re[단위㎲]
     */
    private double mcaRe;

    /**
     * MC A 평균 Lee[단위㎲]
     */
    private double mcaLee;

    /**
     * MC C 평균 Rs[단위㎲]
     */
    private double mccRs;

    /**
     * MC C 평균 Re[단위㎲]
     */
    private double mccRe;

    /**
     * MC C 평균 Lee[단위㎲]
     */
    private double mccLee;

    /**
     * Others 평균 Rs[단위㎲]
     */
    private double othersRs;

    /**
     * Others 평균 Re[단위㎲]
     */
    private double othersRe;

    /**
     * Others 평균 Lee[단위㎲]
     */
    private double othersLee;

    public AvgClienttimeDailyDto(String ymd, String service) {
        this.ymd = ymd;
        this.service = service;
    }

    /**
     * 이 Reference의 키 값을 취한다.
     * @return
     */
    public String getKey() {
        return this.ymd + "_" + this.service;
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

    public void setMCA(AvgClienttimeDailyVO vo){
        this.setMcaRs(vo.getAvgRs());
        this.setMcaRe(vo.getAvgRe());
        this.setMcaLee(vo.getAvgLee());
    }
    
    public double getMcaRs() {
        return mcaRs;
    }

    public void setMcaRs(double mcaRs) {
        this.mcaRs = mcaRs;
    }

    public double getMcaRe() {
        return mcaRe;
    }

    public void setMcaRe(double mcaRe) {
        this.mcaRe = mcaRe;
    }

    public double getMcaLee() {
        return mcaLee;
    }

    public void setMcaLee(double mcaLee) {
        this.mcaLee = mcaLee;
    }

    
    public void setMCC(AvgClienttimeDailyVO vo){
        this.setMccRs(vo.getAvgRs());
        this.setMccRe(vo.getAvgRe());
        this.setMccLee(vo.getAvgLee());
    }
    
    public double getMccRs() {
        return mccRs;
    }

    public void setMccRs(double mccRs) {
        this.mccRs = mccRs;
    }

    public double getMccRe() {
        return mccRe;
    }

    public void setMccRe(double mccRe) {
        this.mccRe = mccRe;
    }

    public double getMccLee() {
        return mccLee;
    }

    public void setMccLee(double mccLee) {
        this.mccLee = mccLee;
    }

    public void setOthers(AvgClienttimeDailyVO vo){
        this.setOthersRs(vo.getAvgRs());
        this.setOthersRe(vo.getAvgRe());
        this.setOthersLee(vo.getAvgLee());
    }
    
    public double getOthersRs() {
        return othersRs;
    }

    public void setOthersRs(double othersRs) {
        this.othersRs = othersRs;
    }

    public double getOthersRe() {
        return othersRe;
    }

    public void setOthersRe(double othersRe) {
        this.othersRe = othersRe;
    }

    public double getOthersLee() {
        return othersLee;
    }

    public void setOthersLee(double othersLee) {
        this.othersLee = othersLee;
    }

    /**
     * 이 class를 CSV 값으로 변활할 때, CSV 데이터의 헤더를 반환한다.
     * @return
     */
    public static final String getCSVHeader() {
        return "일자,서비스,MC A' rs 평균지연,MC A' re 평균지연,MC A' lee 평균지연,MC C' rs 평균지연, MC C' re 평균지연,MC C' lee 평균지연,Others' rs 평균지연, Others' re 평균지연,Others' lee 평균지연";
    }

    /**
     * Object Value를 CSV형식으로 반환한다.
     * @return
     */
    public String getCSVValue() {

        return ymd + "," + service + "," + mcaRs + "," + mcaRe + "," + mcaLee +
                "," + mccRs + "," + mccRe + "," + mccLee +
                "," + othersRs + "," + othersRe + "," + othersLee;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " [ymd=" + ymd + ", service=" + service
                + ", mcaRs=" + mcaRs + ", mcaRe=" + mcaRe + ", mcaLee="
                + mcaLee + ", mccRs=" + mccRs + ", mccRe=" + mccRe
                + ", mccLee=" + mccLee + ", othersRs=" + othersRs
                + ", othersRe=" + othersRe + ", othersLee=" + othersLee + "]";
    }

}
