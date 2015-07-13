package com.lge.cto.swinfra.tiger.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * IP 대역별 HttpStatus 통계 DTO
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@XmlRootElement
public class HttpStatusOfIPRangeDto {

    /**
     * IP 대역(mc_a, mc_c, other)
     */
    private String ip_subset;

    /**
     * HttpStatus Code (e.g., 200)
     */
    private String httpStatus;

    /**
     * 횟수
     */
    private int count;

    public String getIp_subset() {
        return ip_subset;
    }

    public void setIp_subset(String ip_subset) {
        this.ip_subset = ip_subset;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int cnt) {
        this.count = cnt;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " - ip_subset: " + ip_subset
                + ", httpStatus: " + httpStatus + " , Count: " + count;
    }
}
