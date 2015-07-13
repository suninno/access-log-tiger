package com.lge.cto.swinfra.tiger.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * URI 이력 비교 정보 DTO (특정 URI Path에 있어서)
 * 
 * @author suninno.kim
 * @date 2014. 6. 30.
 */
@XmlRootElement
public class UriHistoryDto {
	
	/**
	 * primary key
	 */
	private String minute; //hh-mm
	
	
	/**
	 * 타겟 대상의 호출 횟수
	 */
	private int curRowCnt;
	
	/**
	 * 타겟 대상의 평균 지연 
	 */
	private long curAvg;

	/**
	 * 타겟 대상의 하루 전 호출 횟수
	 */
	private int yesterDayRowCnt;
	
	/**
	 * 타겟 대상의 하루 전 평균 지연
	 */
	private long yesterDayAvg;
	
	/**
	 * 타겟 대상의 1주일 전 호출 횟수
	 */
	private int aWeekAgoRowCnt;

	/**
	 * 타겟 대상의 1주일 전 평균 지연
	 */
	private long aWeekAgoAvg;
	
	
	
	/**
	 * Builder Pattern
	 */
	public static class Builder {
		
		//Required
		private final String minute;
		
		//Optional
		private int curRowCnt;
		private long curAvg;
		private int yesterDayRowCnt;
		private long yesterDayAvg;
		private int aWeekAgoRowCnt;
		private long aWeekAgoAvg;
		
		
		public Builder(String time){
			this.minute	= time;
		}
		
		public Builder curRowCnt(int val){
			this.curRowCnt	= val;
			return this;
		}
		
		public Builder curAvg(long val){
			this.curAvg	= val;
			return this;
		}
		
		public Builder aWeekAgoRowCnt(int val){
			this.aWeekAgoRowCnt	= val;
			return this;
		}
		
		public Builder aWeekAgoAvg(long val){
			this.aWeekAgoAvg	= val;
			return this;
		}
		
		public Builder yesterDayRowCnt(int val){
			this.yesterDayRowCnt	= val;
			return this;
		}
		
		public Builder yesterDayAvg(long val){
			this.yesterDayAvg	= val;
			return this;
		}

		public UriHistoryDto build(){
			return new UriHistoryDto(this);
		}
	}

	
	private UriHistoryDto(Builder builder){
		minute	= builder.minute;
		curRowCnt	= builder.curRowCnt;
		curAvg		= builder.curAvg;
		yesterDayRowCnt	= builder.yesterDayRowCnt;
		yesterDayAvg	= builder.yesterDayAvg;
		aWeekAgoRowCnt	= builder.aWeekAgoRowCnt;
		aWeekAgoAvg		= builder.aWeekAgoAvg;
	}


	public String getMinute() {
		return minute;
	}


	public void setMinute(String minute) {
		this.minute = minute;
	}


	public int getCurRowCnt() {
		return curRowCnt;
	}


	public void setCurRowCnt(int curRowCnt) {
		this.curRowCnt = curRowCnt;
	}


	public long getCurAvg() {
		return curAvg;
	}


	public void setCurAvg(long curAvg) {
		this.curAvg = curAvg;
	}

	public int getYesterDayRowCnt() { return this.yesterDayRowCnt; }
	public void setYesterDayRowCnt(int yesterDayRowCnt) { this.yesterDayRowCnt = yesterDayRowCnt; }
	
	public long getYesterDayAvg() { return this.yesterDayAvg; }
	public void setYesterDayAvg(long yesterDayAvg) { this.yesterDayAvg	= yesterDayAvg; }
	

	public int getaWeekAgoRowCnt() {
		return aWeekAgoRowCnt;
	}


	public void setaWeekAgoRowCnt(int aWeekAgoRowCnt) {
		this.aWeekAgoRowCnt = aWeekAgoRowCnt;
	}


	public long getaWeekAgoAvg() {
		return aWeekAgoAvg;
	}


	public void setaWeekAgoAvg(long aWeekAgoAvg) {
		this.aWeekAgoAvg = aWeekAgoAvg;
	}
	
}
