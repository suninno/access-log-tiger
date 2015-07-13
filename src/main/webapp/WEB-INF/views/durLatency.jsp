<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %> 

<%@ page language="java" contentType="text/html; charset=UTF-8"
import="java.util.*"
 %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">


.table {display: table; }
.tr {display:table-row; }
.td {
	display:table-cell; 
	font-size:small;
	color:dimgray;
	}

.error {
	color: #ff0000;
}
 
.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}

.container{
	text-align:center;
	width:100%;
	height: 200px;
/* 	border: 1px solid black; */
}

.item{
	position: relative;
/* 	height: 30px; width:100px; */
/* 	background: blue; */
/* 	height: 300px; */
	width:350px;
	float: left;
	display: inline-block;
	margin: 0;
/* 	background: transparent url(www.google.com/images/loading.gif) no-repeat center center; */
}

</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>서비스별 기간 지연 정보(Tiger - JIRA Monitoring)</title>

 <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript">

    
    var isError		= "${error}";

	var sdatetime	= '${durationLatencySearchForm.sdatetime}';
	var edatetime	= '${durationLatencySearchForm.edatetime}';
	var logtype 	= '${durationLatencySearchForm.logType}';
	var service		= '${durationLatencySearchForm.service}';

	
	function openCSV(){
 		var url = location.protocol + "//" + location.host + "/tiger/";
		url += "rest/durlatency/search.csv?sdatetime="+ document.getElementById('sdatetime').value +"&edatetime="+ document.getElementById('edatetime').value 
			+"&logType="+document.getElementById('logType').value+"&service="+document.getElementById('service').value;
		window.open(url);
		return;
	}
	
    // Load the Visualization API and the piechart package.
    google.load('visualization', '1.0', {'packages':['corechart']});
    
    // Set a callback to run when the Google Visualization API is loaded.
    google.setOnLoadCallback(drawChart);

	var baseUrl = location.protocol + "//" + location.host + "/tiger/"; 

	
    function drawChart() {

    	
    	if (isError) { return; }
    	if (sdatetime.length != 16) { return; }
    	if (edatetime.length != 16) { return; }
    	
    	var sumavgData = {};
    	sumavgData['collab1']	= 0;
    	sumavgData['crowd']	= 0;
    	sumavgData['jira1']	= 0;
    	sumavgData['jira2']	= 0;
    	sumavgData['jira3']	= 0;
    	sumavgData['jira4']	= 0;
    	sumavgData['jira5']	= 0;
    	sumavgData['jira6']	= 0;
    	sumavgData['jira7']	= 0;
    	sumavgData['jira_tv']	= 0;
    	sumavgData['jira_harmony']	= 0;
    	
    	var sumcntData = {};
    	sumcntData['collab1']	= 0;
    	sumcntData['crowd']	= 0;
    	sumcntData['jira1']	= 0;
    	sumcntData['jira2']	= 0;
    	sumcntData['jira3']	= 0;
    	sumcntData['jira4']	= 0;
    	sumcntData['jira5']	= 0;
    	sumcntData['jira6']	= 0;
    	sumcntData['jira7']	= 0;
    	sumcntData['jira_tv']	= 0;
    	sumcntData['jira_harmony']	= 0;
    	
    	
    	var count = 0;
    	
    	// rs 평균 - JIRA3
		var countData = new google.visualization.DataTable();
		countData.addColumn('string', '시간대');
		
    	// IP 대역별 평균 지연 - JIRA3
		var avgRestData = new google.visualization.DataTable();
		avgRestData.addColumn('string', '시간대');

		if ( service == "all") {
			countData.addColumn('number', 'collab1');
			countData.addColumn('number', 'crowd');
			countData.addColumn('number', 'jira1');
			countData.addColumn('number', 'jira2');
			countData.addColumn('number', 'jira3');
			countData.addColumn('number', 'jira4');
			countData.addColumn('number', 'jira5');
			countData.addColumn('number', 'jira6');
			countData.addColumn('number', 'jira7');
			countData.addColumn('number', 'jira_tv');
			countData.addColumn('number', 'jira_harmony');
			
			avgRestData.addColumn('number', 'collab1');
			avgRestData.addColumn('number', 'crowd');
			avgRestData.addColumn('number', 'jira1');
			avgRestData.addColumn('number', 'jira2');
			avgRestData.addColumn('number', 'jira3');
			avgRestData.addColumn('number', 'jira4');
			avgRestData.addColumn('number', 'jira5');
			avgRestData.addColumn('number', 'jira6');
			avgRestData.addColumn('number', 'jira7');
			avgRestData.addColumn('number', 'jira_tv');
			avgRestData.addColumn('number', 'jira_harmony');
		} else {
			avgRestData.addColumn('number', service);
			countData.addColumn('number', service);
		}



		
		var responseJsonData = $.ajax({
			type:"GET",
			url: baseUrl + "rest/durlatency/search?sdatetime="+ sdatetime +"&edatetime="+edatetime +"&logType="+logtype+"&service="+service,

			dataType:"json",
			async: false,
			success: res_json
		});
		
		function res_json(json){
	
		    if(json.length == 0 ) {  
		    	document.getElementById('message_div').innerHTML = "검색된 결과가 없습니다.";
		    }
		    
			$.each(json, function(key){
				
				count++;
				
				if ( service == "all"){
					avgRestData.addRow([ json[key].calledTime, json[key].avgRest_collab1, json[key].avgRest_crowd, json[key].avgRest_jira1, 
					                     json[key].avgRest_jira2, json[key].avgRest_jira3, json[key].avgRest_jira4, json[key].avgRest_jira5,
					                     json[key].avgRest_jira6, json[key].avgRest_jira7, json[key].avgRest_jira_tv, json[key].avgRest_jira_harmony]);

			    	sumavgData['collab1']	+=  json[key].avgRest_collab1;
			    	sumavgData['crowd']		+=	json[key].avgRest_crowd;
			    	sumavgData['jira1']		+=	json[key].avgRest_jira1;
			    	sumavgData['jira2']		+=	json[key].avgRest_jira2;
			    	sumavgData['jira3']		+=	json[key].avgRest_jira3;
			    	sumavgData['jira4']		+=	json[key].avgRest_jira4;
			    	sumavgData['jira5']		+=	json[key].avgRest_jira5;
			    	sumavgData['jira6']		+=	json[key].avgRest_jira6;
			    	sumavgData['jira7']		+=	json[key].avgRest_jira7;
			    	sumavgData['jira_tv']	+=	json[key].avgRest_jira_tv;
			    	sumavgData['jira_harmony']	+=	json[key].avgRest_jira_harmony;
			    	
					countData.addRow([ json[key].calledTime, json[key].count_collab1, json[key].count_crowd, json[key].count_jira1,
					                   json[key].count_jira2, json[key].count_jira3, json[key].count_jira4, json[key].count_jira5,
					                   json[key].count_jira6, json[key].count_jira7, json[key].count_jira_tv, json[key].count_jira_harmony]);

			    	sumcntData['collab1']	+=	json[key].count_collab1;
			    	sumcntData['crowd']		+=	json[key].count_crowd;
			    	sumcntData['jira1']		+=	json[key].count_jira1;
			    	sumcntData['jira2']		+=	json[key].count_jira2;
			    	sumcntData['jira3']		+=	json[key].count_jira3;
			    	sumcntData['jira4']		+=	json[key].count_jira4;
			    	sumcntData['jira5']		+=	json[key].count_jira5;
			    	sumcntData['jira6']		+=	json[key].count_jira6;
			    	sumcntData['jira7']		+=	json[key].count_jira7;
			    	sumcntData['jira_tv']	+=	json[key].count_jira_tv;
			    	sumcntData['jira_harmony']	+= json[key].count_jira_harmony;
			    	
				} else {
					avgRestData.addRow([ json[key].calledTime, json[key]["avgRest_" + service]]);
					countData.addRow([ json[key].calledTime, json[key]["count_" + service]]);
					
					sumavgData[service] += json[key]["avgRest_" + service];
					sumcntData[service] += json[key]["count_" + service];
				}
			});
		}


		
		var avgRestChart_Title	= "";
		var countChart_Title	= "";
		
		if( service == "all") {
			avgRestChart_Title = " (collab1:" + parseInt(sumavgData.collab1/count)+", crowd:"+ parseInt(sumavgData.crowd/count) +", jira1:"+ parseInt(sumavgData.jira1/count);
			avgRestChart_Title += ", jira2:"+ parseInt(sumavgData.jira2/count);
			avgRestChart_Title += ", jira3:"+ parseInt(sumavgData.jira3/count);
			avgRestChart_Title += ", jira4:"+ parseInt(sumavgData.jira4/count);
			avgRestChart_Title += ", jira5:"+ parseInt(sumavgData.jira5/count);
			avgRestChart_Title += ", jira6:"+ parseInt(sumavgData.jira6/count);
			avgRestChart_Title += ", jira7:"+ parseInt(sumavgData.jira7/count);
			avgRestChart_Title += ", jira_tv:"+ parseInt(sumavgData.jira_tv/count);
			avgRestChart_Title += ", jira_harmony:"+ parseInt(sumavgData.jira_harmony/count);
			avgRestChart_Title += ")";
			
			countChart_Title = " (collab1:" + parseInt(sumcntData.collab1/count);
			countChart_Title += " , crowd:" + parseInt(sumcntData.crowd/count);
			countChart_Title += " , jira1:" + parseInt(sumcntData.jira1/count);
			countChart_Title += " , jira2:" + parseInt(sumcntData.jira2/count);
			countChart_Title += " , jira3:" + parseInt(sumcntData.jira3/count);
			countChart_Title += " , jira4:" + parseInt(sumcntData.jira4/count);
			countChart_Title += " , jira5:" + parseInt(sumcntData.jira5/count);
			countChart_Title += " , jira6:" + parseInt(sumcntData.jira6/count);
			countChart_Title += " , jira7:" + parseInt(sumcntData.jira7/count);
			countChart_Title += " , jira_tv:" + parseInt(sumcntData.jira_tv/count);
			countChart_Title += " , jira_harmony:" + parseInt(sumcntData.jira_harmony/count);
			countChart_Title += ")";
			
		} else {
			avgRestChart_Title	= " ("+ service +":" + parseInt(sumavgData[service]/count)+" )";
			countChart_Title	= " ("+ service +":" + parseInt(sumcntData[service]/count)+" )";
		}
		
		var options = {
		          title: '평균 지연',
		          hAxis: {title: '일시' + avgRestChart_Title, titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
		          vAxis: {title: '평균지연시간[㎲]', textStyle: {fontSize:12}},
		          
    	          explorer: {maxZoomOut:4, keepInBounds: true},
                  'sliceVisibilityThreshold':0
		        };
	    var avgRestchart = new google.visualization.AreaChart(document.getElementById('avgRestchart_div'));
	    avgRestchart.draw(avgRestData, options);
	    
	    
		
		var options = {
		          title: '호출 횟수',
		          hAxis: {title: '일시' + countChart_Title, titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
		          vAxis: {title: '수', textStyle: {fontSize:12}},
    	          explorer: {maxZoomOut:4, keepInBounds: true},
                  sliceVisibilityThreshold:0
		        };
	    var countchart = new google.visualization.AreaChart(document.getElementById('countchart_div'));
	    countchart.draw(countData, options);

    }
    
    
    
    function changelogType(obj) {
		var box = document.getElementById("service");
	
    	//tomcat
    	if (obj.selectedIndex == 0 ){
    		box.options.length = 0;
			box.options[box.options.length] = new Option('all');
			box.options[box.options.length] = new Option('jira1');
			box.options[box.options.length] = new Option('jira2');
			box.options[box.options.length] = new Option('jira3');
			box.options[box.options.length] = new Option('jira4');
			box.options[box.options.length] = new Option('jira5');
			box.options[box.options.length] = new Option('jira6');
			box.options[box.options.length] = new Option('jira7');
			box.options[box.options.length] = new Option('jira_harmony');
			box.options[box.options.length] = new Option('jira_tv');
			box.options[box.options.length] = new Option('crowd');
    		 
    	//apache
    	} else if (obj.selectedIndex == 1 ){
    		box.options.length = 0;
			box.options[box.options.length] = new Option('all');
			box.options[box.options.length] = new Option('apache1');
			box.options[box.options.length] = new Option('apache2');
    	}
    }

	</script>    
</head>
<body>

	
<table width="100%">
	<tr>
		<td align="left">
			<div align="left" style="font-size:small; color:dimgray;">
				<a href="/tiger/">상위 페이지</a></div>
		</td>
		<td>
			<div align="right" style="font-size:small; color:dimgray;">서버 시각：${serverTime}</div>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<div align="right" style="font-size:small; color:dimgray;">Version：<spring:eval expression="@propertyConfigurer['config.version']" /></div>
		</td>
	</tr>
</table>


<center>
<h2>서비스별 기간 지연 정보</h2>
</center>


<!-- 검색 폼 -->
<form:form method="POST" modelAttribute="durationLatencySearchForm" commandName="durationLatencySearchForm" action="/tiger/dur-latency/">

	<form:errors path="*" cssClass="errorblock" element="div" />
	
	<p>
		<form:label path="sdatetime" for="sdatetime">검색기간</form:label>
		<form:input path="sdatetime" type="text" size="16" placeholder="YYYY-MM-DD hh:mm" />
		~ <form:input path="edatetime" type="text" size="16" placeholder="YYYY-MM-DD hh:mm" />
		
		
		<form:select id="logType" path="logType" for="logType" onchange="changelogType(this);">
			<form:options items="${logTypeList}"></form:options>
		</form:select>
		
		<form:select id="service" path="service" for="service">
			<form:options items='${serviceList}' />
		</form:select>
		
		<input type="submit" value="검색" />
		<input type="button" value="csv..." onclick="openCSV();" />	
	</p>
</form:form>

<div style="font-size:small; font-style: italic;">
검색기간이 1일 이하이면, 분 단위로 집계한다. 1일을 초과하면, 시간 단위로 집계한다. 30일을 초과하면, 일 단위로 집계한다. 
</div>	


<div id="message_div" style="text-align:center; color:red;"></div>


<center>
<div id="avgRestchart_div" style="height: 350px;"></div>
<div id="countchart_div" style="height: 350px;"></div>
</center>

</body>
</html>