package com.lge.cto.swinfra.tiger.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * HttpStatus 통계 DTO
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@XmlRootElement
public class HttpStatusDto {

    /**
     * HttpStatus Code (e.g., 200)
     */
    private String httpStatus;

    /**
     * 횟수
     */
    private int count;

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
        return this.getClass().getName() + " - httpStatus: " + httpStatus
                + " , Count: " + count;
    }
}
