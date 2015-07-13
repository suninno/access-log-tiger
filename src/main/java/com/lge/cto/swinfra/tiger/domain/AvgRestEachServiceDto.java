package com.lge.cto.swinfra.tiger.domain;

import javax.xml.bind.annotation.XmlRootElement;

import com.lge.cto.swinfra.tiger.vo.AvgRestofUriVO;

/**
 * 각 서비스별 평균 지연 및 호출 횟수 DTO
 * 
 * @author suninno.kim
 * @date 2014. 4. 7.
 */
@XmlRootElement
public class AvgRestEachServiceDto {

	/**
	 * csv 출력을 위한 시각
	 */
	private String time4csv;

	/**
	 * 시각
	 */
	private String calledTime;

	private double avgRest_collab1;
	private double avgRest_crowd;
	private double avgRest_jira1;
	private double avgRest_jira2;
	private double avgRest_jira3;
	private double avgRest_jira4;
	private double avgRest_jira5;
	private double avgRest_jira6;
	private double avgRest_jira7;
	private double avgRest_jira_tv;
	private double avgRest_jira_harmony;

	private int count_collab1;
	private int count_crowd;
	private int count_jira1;
	private int count_jira2;
	private int count_jira3;
	private int count_jira4;
	private int count_jira5;
	private int count_jira6;
	private int count_jira7;
	private int count_jira_tv;
	private int count_jira_harmony;

	public AvgRestEachServiceDto(String time4csv, String calledTime) {
		this.time4csv = time4csv;
		this.calledTime = calledTime;
	}

	public String getTime4csv() {
		return time4csv;
	}

	public void setTime4csv(String time4csv) {
		this.time4csv = time4csv;
	}

	public String getCalledTime() {
		return calledTime;
	}

	public void setCalledTime(String calledTime) {
		this.calledTime = calledTime;
	}

	public double getAvgRest_collab1() {
		return avgRest_collab1;
	}

	public void setCollab1(AvgRestofUriVO vo){
		this.setAvgRest_collab1(vo.getAvgRest());
		this.setCount_collab1(vo.getCount());
	}
	
	public void setAvgRest_collab1(double avgRest_collab1) {
		this.avgRest_collab1 = avgRest_collab1;
	}

	public double getAvgRest_crowd() {
		return avgRest_crowd;
	}

	public void setCrowd(AvgRestofUriVO vo){
		this.setAvgRest_crowd(vo.getAvgRest());
		this.setCount_crowd(vo.getCount());
	}
	
	public void setAvgRest_crowd(double avgRest_crowd) {
		this.avgRest_crowd = avgRest_crowd;
	}

	public double getAvgRest_jira1() {
		return avgRest_jira1;
	}

	public void setJira1(AvgRestofUriVO vo){
		this.setAvgRest_jira1(vo.getAvgRest());
		this.setCount_jira1(vo.getCount());
	}
	
	public void setAvgRest_jira1(double avgRest_jira1) {
		this.avgRest_jira1 = avgRest_jira1;
	}

	public double getAvgRest_jira2() {
		return avgRest_jira2;
	}

	public void setJira2(AvgRestofUriVO vo){
		this.setAvgRest_jira2(vo.getAvgRest());
		this.setCount_jira2(vo.getCount());
	}
	
	public void setAvgRest_jira2(double avgRest_jira2) {
		this.avgRest_jira2 = avgRest_jira2;
	}

	public void setJira3(AvgRestofUriVO vo){
		this.setAvgRest_jira3(vo.getAvgRest());
		this.setCount_jira3(vo.getCount());
	}
	
	public double getAvgRest_jira3() {
		return avgRest_jira3;
	}

	public void setAvgRest_jira3(double avgRest_jira3) {
		this.avgRest_jira3 = avgRest_jira3;
	}

	public void setJira4(AvgRestofUriVO vo){
		this.setAvgRest_jira4(vo.getAvgRest());
		this.setCount_jira4(vo.getCount());
	}
	
	public double getAvgRest_jira4() {
		return avgRest_jira4;
	}

	public void setAvgRest_jira4(double avgRest_jira4) {
		this.avgRest_jira4 = avgRest_jira4;
	}

	public void setJira5(AvgRestofUriVO vo){
		this.setAvgRest_jira5(vo.getAvgRest());
		this.setCount_jira5(vo.getCount());
	}
	
	public double getAvgRest_jira5() {
		return avgRest_jira5;
	}

	public void setAvgRest_jira5(double avgRest_jira5) {
		this.avgRest_jira5 = avgRest_jira5;
	}

	
	public void setJira6(AvgRestofUriVO vo){
		this.setAvgRest_jira6(vo.getAvgRest());
		this.setCount_jira6(vo.getCount());
	}

	public double getAvgRest_jira6() {
		return avgRest_jira6;
	}

	public void setAvgRest_jira6(double avgRest_jira6) {
		this.avgRest_jira6 = avgRest_jira6;
	}

	public void setJira7(AvgRestofUriVO vo){
		this.setAvgRest_jira7(vo.getAvgRest());
		this.setCount_jira7(vo.getCount());
	}
	
	public double getAvgRest_jira7() {
		return avgRest_jira7;
	}

	public void setAvgRest_jira7(double avgRest_jira7) {
		this.avgRest_jira7 = avgRest_jira7;
	}
	
	public void setJiraTv(AvgRestofUriVO vo){
		this.setAvgRest_jira_tv(vo.getAvgRest());
		this.setCount_jira_tv(vo.getCount());
	}


	public double getAvgRest_jira_tv() {
		return avgRest_jira_tv;
	}

	public void setAvgRest_jira_tv(double avgRest_jira_tv) {
		this.avgRest_jira_tv = avgRest_jira_tv;
	}

	
	public void setJiraHarmony(AvgRestofUriVO vo){
		this.setAvgRest_jira_harmony(vo.getAvgRest());
		this.setCount_jira_harmony(vo.getCount());
	}

	
	public double getAvgRest_jira_harmony() {
		return avgRest_jira_harmony;
	}

	public void setAvgRest_jira_harmony(double avgRest_jira_harmony) {
		this.avgRest_jira_harmony = avgRest_jira_harmony;
	}

	public int getCount_collab1() {
		return count_collab1;
	}

	public void setCount_collab1(int count_collab1) {
		this.count_collab1 = count_collab1;
	}

	public int getCount_crowd() {
		return count_crowd;
	}

	public void setCount_crowd(int count_crowd) {
		this.count_crowd = count_crowd;
	}

	public int getCount_jira1() {
		return count_jira1;
	}

	public void setCount_jira1(int count_jira1) {
		this.count_jira1 = count_jira1;
	}

	public int getCount_jira2() {
		return count_jira2;
	}

	public void setCount_jira2(int count_jira2) {
		this.count_jira2 = count_jira2;
	}

	public int getCount_jira3() {
		return count_jira3;
	}

	public void setCount_jira3(int count_jira3) {
		this.count_jira3 = count_jira3;
	}

	public int getCount_jira4() {
		return count_jira4;
	}

	public void setCount_jira4(int count_jira4) {
		this.count_jira4 = count_jira4;
	}

	public int getCount_jira5() {
		return count_jira5;
	}

	public void setCount_jira5(int count_jira5) {
		this.count_jira5 = count_jira5;
	}

	public int getCount_jira6() {
		return count_jira6;
	}

	public void setCount_jira6(int count_jira6) {
		this.count_jira6 = count_jira6;
	}

	public int getCount_jira7() {
		return count_jira7;
	}

	public void setCount_jira7(int count_jira7) {
		this.count_jira7 = count_jira7;
	}

	public int getCount_jira_tv() {
		return count_jira_tv;
	}

	public void setCount_jira_tv(int count_jira_tv) {
		this.count_jira_tv = count_jira_tv;
	}

	public int getCount_jira_harmony() {
		return count_jira_harmony;
	}

	public void setCount_jira_harmony(int count_jira_harmony) {
		this.count_jira_harmony = count_jira_harmony;
	}

	/**
	 * 이 reference를 CSV 값으로 변활할 때, CSV 데이터의 헤더를 반환한다.
	 * 
	 * @return
	 */
	public static final String getCSVHeader() {
		return "시각, collab1 평균지연[㎲], collab1 횟수, crowd 평균지연[㎲], crowd 횟수, jira1 평균지연[㎲], jira1 횟수, jira2 평균지연[㎲], jira2 횟수"
				+ ", jira3 평균지연[㎲], jira3 횟수, jira4 평균지연[㎲], jira4 횟수, jira5 평균지연[㎲], jira5 횟수, jira6 평균지연[㎲], jira6 횟수"
				+ ", jira7 평균지연[㎲], jira7 횟수, jira_tv 평균지연[㎲], jira_tv 횟수, jira_harmony 평균지연[㎲], jira_harmony 횟수";
	}

	/**
	 * Object Value를 CSV형식으로 반환한다.
	 * 
	 * @return
	 */

	public String getCSVValue() {

		return time4csv + "," + avgRest_collab1 + "," + count_collab1 + ","
				+ avgRest_crowd + "," + count_crowd + "," + avgRest_jira1 + ","
				+ count_jira1 + "," + avgRest_jira2 + "," + count_jira2 + ","
				+ avgRest_jira3 + "," + count_jira3 + "," + avgRest_jira4 + ","
				+ count_jira4 + "," + avgRest_jira5 + "," + count_jira5 + ","
				+ avgRest_jira6 + "," + count_jira6 + "," + avgRest_jira7 + ","
				+ count_jira7 + "," + avgRest_jira_tv + "," + count_jira_tv
				+ "," + avgRest_jira_harmony + "," + count_jira_harmony;
	}
}
