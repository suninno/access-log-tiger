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

<title>ClientTime 시간별 통계(Tiger - JIRA Monitoring)</title>

 <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript">

    var isError = "${error}";
//     if (isError) { return }


	function openCSV(){
 		var url = location.protocol + "//" + location.host + "/tiger/"; 
		url += "rest/clienttime/hourly/search.csv?date=" + document.getElementById('searchDate').value;
		window.open(url);
		return;
	}
		

	var sdate	= '${latencyPerIPSearchForm.searchDate}';
	var logType = '${latencyPerIPSearchForm.logType}';
    
    // Load the Visualization API and the piechart package.
    google.load('visualization', '1.0', {'packages':['corechart']});
    
    // Set a callback to run when the Google Visualization API is loaded.
    google.setOnLoadCallback(drawChart);

	var baseUrl = location.protocol + "//" + location.host + "/tiger/"; 

    function drawChart() {
    
    	if (sdate.length != 10) { return; }

		
    	// JIRA3 - Rs
		var RsData = new google.visualization.DataTable();
		RsData.addColumn('string', '시간대');
		RsData.addColumn('number', 'MC A');
		RsData.addColumn('number', 'MC C');
		RsData.addColumn('number', 'Others');
	
    	// JIRA3 - Rs
		var ReData = new google.visualization.DataTable();
		ReData.addColumn('string', '시간대');
		ReData.addColumn('number', 'MC A');
		ReData.addColumn('number', 'MC C');
		ReData.addColumn('number', 'Others');
	
		
    	// JIRA3 - Lee
		var leeData = new google.visualization.DataTable();
		leeData.addColumn('string', '시간대');
		leeData.addColumn('number', 'MC A');
		leeData.addColumn('number', 'MC C');
		leeData.addColumn('number', 'Others');
		

    	// JIRA3 - Re - Rs
		var diffData = new google.visualization.DataTable();
		diffData.addColumn('string', '시간대');
		diffData.addColumn('number', 'MC A');
		diffData.addColumn('number', 'MC C');
		diffData.addColumn('number', 'Others');
		
		var responseJsonData = $.ajax({
			type:"GET",
			url: baseUrl + "rest/clienttime/hourly/search?date=" + sdate+"&service=jira3",
			dataType:"json",
			async: false,
			success: jira3_json
		});
		
		function jira3_json(json){
	
		    if(json.length == 0 ) {  
		    	document.getElementById('message_div').innerHTML = "JIRA3에 검색된 결과가 없습니다.";
		    	return;
		    }
		    
			$.each(json, function(key){
				RsData.addRow([ json[key].hh, json[key].mcaRs, json[key].mccRs, json[key].othersRs  ]);
				ReData.addRow([ json[key].hh, json[key].mcaRe, json[key].mccRe, json[key].othersRe  ]);
				leeData.addRow([ json[key].hh, json[key].mcaLee, json[key].mccLee, json[key].othersLee  ]);
				diffData.addRow([ json[key].hh, json[key].mcaRe - json[key].mcaRs, 
				                json[key].mccRe - json[key].mccRs, json[key].othersRe - json[key].othersRs ]);
			});
		}
		
		var options = {
		          title: '평균 지연 rs (JIRA3)',
		          hAxis: {title: '시간대[hh]', titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
		          vAxis: {title: '평균지연시간[㎲]', textStyle: {fontSize:12}},
		        };
	    var jira3_rs_chart = new google.visualization.LineChart(document.getElementById('jira3_rs_chart_div'));
	    jira3_rs_chart.draw(RsData, options);
    
		
		var options = {
		          title: '평균 지연 re (JIRA3)',
		          hAxis: {title: '시간대[hh]', titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
		          vAxis: {title: '평균지연시간[㎲]', textStyle: {fontSize:12}},
		        };
	    var jira3_re_chart = new google.visualization.LineChart(document.getElementById('jira3_re_chart_div'));
	    jira3_re_chart.draw(ReData, options);
    

		var options = {
		          title: '평균 지연 lee (JIRA3)',
		          hAxis: {title: '시간대[hh]', titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
		          vAxis: {title: '평균지연시간[㎲]', textStyle: {fontSize:12}},
		        };
	    var jira3_lee_chart = new google.visualization.LineChart(document.getElementById('jira3_lee_chart_div'));
	    jira3_lee_chart.draw(leeData, options);

		var options = {
		          title: '평균 지연 re - rs (JIRA3)',
		          hAxis: {title: '시간대[hh]', titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
		          vAxis: {title: '평균지연시간[㎲]', textStyle: {fontSize:12}},
		        };
	    var jira3_diff_chart = new google.visualization.LineChart(document.getElementById('jira3_diff_chart_div'));
	    jira3_diff_chart.draw(diffData, options);
	    
    
	    
//-----------------------------------------------------------------------------------------	    
	    
    	// Collab - Rs
		var collabrsData = new google.visualization.DataTable();
		collabrsData.addColumn('string', '시간대');
		collabrsData.addColumn('number', 'MC A');
		collabrsData.addColumn('number', 'MC C');
		collabrsData.addColumn('number', 'Others');
	
		// Collab - Re
		var collabreData = new google.visualization.DataTable();
		collabreData.addColumn('string', '시간대');
		collabreData.addColumn('number', 'MC A');
		collabreData.addColumn('number', 'MC C');
		collabreData.addColumn('number', 'Others');
	
		// Collab - Lee
		var collableeData = new google.visualization.DataTable();
		collableeData.addColumn('string', '시간대');
		collableeData.addColumn('number', 'MC A');
		collableeData.addColumn('number', 'MC C');
		collableeData.addColumn('number', 'Others');

		// Collab - (Re - Rs)
		var collabdiffData = new google.visualization.DataTable();
		collabdiffData.addColumn('string', '시간대');
		collabdiffData.addColumn('number', 'MC A');
		collabdiffData.addColumn('number', 'MC C');
		collabdiffData.addColumn('number', 'Others');
	
		
		var responseJsonData = $.ajax({
			type:"GET",
			url: baseUrl + "rest/clienttime/hourly/search?date=" + sdate+"&service=collab1" ,
			dataType:"json",
			async: false,
			success: collabrs_json
		});
		
		function collabrs_json(json){
	
		    if(json.length == 0 ) {  
		    	document.getElementById('message_div').innerHTML = "Collab에 대한 검색된 결과가 없습니다.";
		    	return;
		    }
		    
			$.each(json, function(key){
				collabrsData.addRow([ json[key].hh, json[key].mcaRs, json[key].mccRs, json[key].othersRs  ]);
				
				collabreData.addRow([ json[key].hh, json[key].mcaRe, json[key].mccRe, json[key].othersRe  ]);
				
				collableeData.addRow([ json[key].hh, json[key].mcaLee, json[key].mccLee, json[key].othersLee  ]);
				
				collabdiffData.addRow([ json[key].hh, json[key].mcaRe - json[key].mcaRs, json[key].mccRe - json[key].mccRs, json[key].othersRe - json[key].othersRs  ]);
			});    		
		}
		
		var options = {
		          title: '평균 지연 rs (collab)',
		          hAxis: {title: '시간대[hh]', titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
		          vAxis: {title: '평균지연시간[㎲]', textStyle: {fontSize:12}},
		        };
	    var collab_rs_chart = new google.visualization.LineChart(document.getElementById('collab_rs_chart_div'));
	    collab_rs_chart.draw(collabrsData, options);
	    

		
		var options = {
		          title: '평균 지연 re (collab)',
		          hAxis: {title: '시간대[hh]', titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
		          vAxis: {title: '평균지연시간[㎲]', textStyle: {fontSize:12}},
		        };
	    var collab_re_chart = new google.visualization.LineChart(document.getElementById('collab_re_chart_div'));
	    collab_re_chart.draw(collabreData, options);	    
	  
		
		var options = {
		          title: '평균 지연 lee (collab)',
		          hAxis: {title: '시간대[hh]', titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
		          vAxis: {title: '평균지연시간[㎲]', textStyle: {fontSize:12}},
		        };
	    var collab_lee_chart = new google.visualization.LineChart(document.getElementById('collab_lee_chart_div'));
	    collab_lee_chart.draw(collableeData, options);	    
	    	    
		
		var options = {
		          title: '평균 지연 re - rs (collab)',
		          hAxis: {title: '시간대[hh]', titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
		          vAxis: {title: '평균지연시간[㎲]', textStyle: {fontSize:12}},
		        };
	    var collab_diff_chart = new google.visualization.LineChart(document.getElementById('collab_diff_chart_div'));
	    collab_diff_chart.draw(collabdiffData, options);	  	    
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
<h2>Clienttime 시간별 통계</h2>
</center>


<!-- 검색 폼 -->
<form:form method="POST" modelAttribute="latencyPerIPSearchForm" commandName="latencyPerIPSearchForm" action="/tiger/clienttime/">

	<form:errors path="*" cssClass="errorblock" element="div" />
	
	<p>
		<form:label path="searchDate" for="searchDate">검색일자</form:label>
		<form:input path="searchDate" type="text" size="10" placeholder="YYYY-MM-DD" />
		
		<input type="submit" value="검색" />
		<input type="button" value="csv..." onclick="openCSV();" />
	</p>	
</form:form>	


<div id="message_div" style="text-align:center; color:red;"></div>


<center>
<p>Status Code[200 ~ 400]에 대한 평균 지연</p>
<div id="jira3_rs_chart_div" style="height: 260px;"></div>
<div id="jira3_re_chart_div" style="height: 260px;"></div>
<div id="jira3_lee_chart_div" style="height: 260px;"></div>
<div id="jira3_diff_chart_div" style="height: 260px;"></div>

<div id="collab_rs_chart_div" style="height: 260px;"></div>
<div id="collab_re_chart_div" style="height: 260px;"></div>
<div id="collab_lee_chart_div" style="height: 260px;"></div>
<div id="collab_diff_chart_div" style="height: 260px;"></div>

</center>

</body>
</html>