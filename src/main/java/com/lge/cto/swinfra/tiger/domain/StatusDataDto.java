package com.lge.cto.swinfra.tiger.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * HttpStatus 통계 DTO
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@XmlRootElement
public class StatusDataDto {

	
	private String ymd;
	
	private long collab1;
	private long crowd;
	private long jira1;
	private long jira2;
	private long jira3;
	private long jira4;
	private long jira5;
	private long jira6;
	private long jira7;
	private long jira_harmony;
	private long jira_tv;
	
	
	public StatusDataDto(String ymd){
		this.ymd = ymd;
	}
	
	public String getYmd() {
		return ymd;
	}
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}
	public long getCollab1() {
		return collab1;
	}
	public void setCollab1(long collab1) {
		this.collab1 = collab1;
	}
	public long getCrowd() {
		return crowd;
	}
	public void setCrowd(long crowd) {
		this.crowd = crowd;
	}
	public long getJira1() {
		return jira1;
	}
	public void setJira1(long jira1) {
		this.jira1 = jira1;
	}
	public long getJira2() {
		return jira2;
	}
	public void setJira2(long jira2) {
		this.jira2 = jira2;
	}
	public long getJira3() {
		return jira3;
	}
	public void setJira3(long jira3) {
		this.jira3 = jira3;
	}
	public long getJira4() {
		return jira4;
	}
	public void setJira4(long jira4) {
		this.jira4 = jira4;
	}
	public long getJira5() {
		return jira5;
	}
	public void setJira5(long jira5) {
		this.jira5 = jira5;
	}
	public long getJira6() {
		return jira6;
	}
	public void setJira6(long jira6) {
		this.jira6 = jira6;
	}
	public long getJira7() {
		return jira7;
	}
	public void setJira7(long jira7) {
		this.jira7 = jira7;
	}
	public long getJira_harmony() {
		return jira_harmony;
	}
	public void setJira_harmony(long jira_harmony) {
		this.jira_harmony = jira_harmony;
	}
	public long getJira_tv() {
		return jira_tv;
	}
	public void setJira_tv(long jira_tv) {
		this.jira_tv = jira_tv;
	}
	@Override
	public String toString() {
		return this.getClass().getName() + "[ymd=" + ymd + ", collab1=" + collab1
				+ ", crowd=" + crowd + ", jira1=" + jira1 + ", jira2=" + jira2
				+ ", jira3=" + jira3 + ", jira4=" + jira4 + ", jira5=" + jira5
				+ ", jira6=" + jira6 + ", jira7=" + jira7 + ", jira_harmony="
				+ jira_harmony + ", jira_tv=" + jira_tv + "]";
	}


}
