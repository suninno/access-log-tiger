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

<title>Source IP별 통계(Tiger - JIRA Monitoring)</title>

 <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript">

    var isError = "${error}";
//     if (isError) { return }

	var sdate	= '${latencyPerIPSearchForm.searchDate}';
	var logType = '${latencyPerIPSearchForm.logType}';
    
    // Load the Visualization API and the piechart package.
    google.load('visualization', '1.0', {'packages':['table']});
    google.load('visualization', '1.0', {'packages':['corechart']});
    
    // Set a callback to run when the Google Visualization API is loaded.
    google.setOnLoadCallback(drawChart);

    
	var baseUrl = location.protocol + "//" + location.host + "/tiger/"; 

    
    function drawChart() {
    
    	if (sdate.length != 10) { return; }

    	//[JIRA3, Collab] 전체 HTTP STATUS 파이 차트
    	//[JIRA3, Collab] 각 대역별 HTTP STATUS 파이 차트
		var mcaStatus = new google.visualization.DataTable();
		mcaStatus.addColumn('string', 'HttpStatus');
		mcaStatus.addColumn('number', '횟수');
    	
		var mccStatus = new google.visualization.DataTable();
		mccStatus.addColumn('string', 'HttpStatus');
		mccStatus.addColumn('number', '횟수');
		
		var othersStatus = new google.visualization.DataTable();
		othersStatus.addColumn('string', 'HttpStatus');
		othersStatus.addColumn('number', '횟수');
		
		var allStatus = new google.visualization.DataTable();
		allStatus.addColumn('string', 'HttpStatus');
		allStatus.addColumn('number', '횟수');
		
		var allStatusData = {};

		
		var responseJsonData = $.ajax({
			type:"GET",
			url: baseUrl + "rest/srcip/httpstatus?date=" + sdate+"&logType="+logType,
			dataType:"json",
			async: false,
			success: status_json
		});
		
		
		function status_json(json){
	
		    if(json.length == 0 ) {  
		    	document.getElementById('message_div').innerHTML = "검색된 결과가 없습니다.";
		    	return;
		    }
		    
			$.each(json, function(key){
				
				if ( json[key].ip_subset == "mc_a") {
					mcaStatus.addRow( [json[key].httpStatus, json[key].count ] );
				} else if ( json[key].ip_subset == "mc_c") {
					mccStatus.addRow( [json[key].httpStatus, json[key].count ] );
				} else if ( json[key].ip_subset == "others") {
					othersStatus.addRow( [json[key].httpStatus, json[key].count ] );
				} else {
					alert("Unknown IP Range: " + json[key].ip_subset )
				}
				

				if (isNaN(allStatusData[ json[key].httpStatus] ) ) {
					allStatusData[ json[key].httpStatus]  = json[key].count;
				} else {
					allStatusData[  json[key].httpStatus  ]
					= allStatusData[  json[key].httpStatus ] + json[key].count;
				}
			});    		
		}
    	
		for (var k in allStatusData){
			allStatus.addRow( [ k, allStatusData[k] ] );
		}
		
    	
        // Set chart options
        var options = {'title':'HttpStatus 통계',
                       'sliceVisibilityThreshold':0
        };
        // Instantiate and draw our chart, passing in some options.
        var http_chart = new google.visualization.PieChart(document.getElementById('httpStatus_div'));
        http_chart.draw(allStatus, options);

    	
        var options = {'title':'MC A 통계',
                'sliceVisibilityThreshold':0
 		};
		var mca_chart = new google.visualization.PieChart(document.getElementById('mcaStatus_div'));
		mca_chart.draw(mcaStatus, options);
		    	
        var options = {'title':'MC C 통계',
                'sliceVisibilityThreshold':0
 		};
		var mcc_chart = new google.visualization.PieChart(document.getElementById('mccStatus_div'));
		mcc_chart.draw(mccStatus, options);
		    		

        var options = {'title':'Others 통계',
                'sliceVisibilityThreshold':0
 		};
		var others_chart = new google.visualization.PieChart(document.getElementById('othersStatus_div'));
		others_chart.draw(othersStatus, options);

    	
		
		
    	// IP 대역별 평균 지연 - JIRA3
		var jira3Data = new google.visualization.DataTable();
		jira3Data.addColumn('string', '시간대');
		jira3Data.addColumn('number', 'MC A');
		jira3Data.addColumn('number', 'MC C');
		jira3Data.addColumn('number', 'Others');
	
		
		mcAAvg = 0;
		mcCAvg = 0;
		otherAvg = 0;
		cnt = 0;
		
		var responseJsonData = $.ajax({
			type:"GET",
			url: baseUrl + "rest/srcip/hourly?date=" + sdate+"&logType="+logType+"&service=jira3",
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
				jira3Data.addRow([ json[key].hh, json[key].mcaLatency, json[key].mccLatency, json[key].othersLatency ]);
				mcAAvg += json[key].mcaLatency;
				mcCAvg += json[key].mccLatency;
				otherAvg += json[key].othersLatency;
				cnt++;
			});    		
		}
		
		var options = {
		          title: 'IP 대역별 평균 지연(JIRA3)',
		          hAxis: {title: '시간대[hh] (mcA:'+ parseInt(mcAAvg/cnt) + ', mcC:'+ parseInt(mcCAvg/cnt) + ', others:' + parseInt(otherAvg/cnt) + ')', titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
		          vAxis: {title: '평균지연시간[㎲]', textStyle: {fontSize:12}},
		        };
	    var jira3chart = new google.visualization.LineChart(document.getElementById('jira3_chart_div'));
	    jira3chart.draw(jira3Data, options);
    

    
    	// IP 대역별 평균 지연 - Collab
		var collabData = new google.visualization.DataTable();
		collabData.addColumn('string', '시간대');
		collabData.addColumn('number', 'MC A');
		collabData.addColumn('number', 'MC C');
		collabData.addColumn('number', 'Others');
	
		
		mcAAvg = 0;
		mcCAvg = 0;
		otherAvg = 0;
		cnt = 0;
		
		var responseJsonData = $.ajax({
			type:"GET",
			url: baseUrl + "rest/srcip/hourly?date=" + sdate+"&logType="+logType+ "&service=collab1" ,
			dataType:"json",
			async: false,
			success: collab_json
		});
		
		function collab_json(json){
	
		    if(json.length == 0 ) {  
		    	document.getElementById('message_div').innerHTML = "Collab에 대한 검색된 결과가 없습니다.";
		    	return;
		    }
		    
			$.each(json, function(key){
				collabData.addRow([ json[key].hh, json[key].mcaLatency, json[key].mccLatency, json[key].othersLatency ]);
				mcAAvg += json[key].mcaLatency;
				mcCAvg += json[key].mccLatency;
				otherAvg += json[key].othersLatency;
				cnt++;
			});    		
		}
		
		var options = {
		          title: 'IP 대역별 평균 지연(collab)',
		          hAxis: {title: '시간대[hh] (mcA:'+ parseInt(mcAAvg/cnt) + ', mcC:'+ parseInt(mcCAvg/cnt) + ', others:' + parseInt(otherAvg/cnt) + ')' , titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
		          vAxis: {title: '평균지연시간[㎲]', textStyle: {fontSize:12}},
		        };
	    var collabchart = new google.visualization.LineChart(document.getElementById('collab_chart_div'));
	    collabchart.draw(collabData, options);

    
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
	<tr>
		<td align="left"><div align="left" style="font-size:small; color:dimgray;">MC A, MC C, 나머지 IP 대역대에 대한 JIRA3, Collab에 관한 통계</div></td>
	</tr>
</table>


<center>
<h2>Source IP별 통계</h2>
</center>


<!-- 검색 폼 -->
<form:form method="POST" modelAttribute="latencyPerIPSearchForm" commandName="latencyPerIPSearchForm" action="/tiger/srcip/">

	<form:errors path="*" cssClass="errorblock" element="div" />
	
	<p>
		<form:label path="searchDate" for="searchDate">검색일자</form:label>
		<form:input path="searchDate" type="text" size="10" placeholder="YYYY-MM-DD" />

		
		<form:select id="logType" path="logType" for="logType" onchange="changelogType(this);">
			<form:options items="${logTypeList}"></form:options>
		</form:select>
		
		
		<input type="submit" value="검색" />
	</p>	
</form:form>	


<div id="message_div" style="text-align:center; color:red;"></div>


<!-- <div class="container"> -->
<!-- 	<div id="httpStatus_div" class="item"></div> -->
<!-- 	<div id="chart_div" class="item"></div> -->
<!-- </div> -->

<div class="container">
	<div id="httpStatus_div" class="item"></div>
	<div id="mcaStatus_div" class="item"></div>
	<div id="mccStatus_div" class="item"></div>
	<div id="othersStatus_div" class="item"></div>
</div>


<center>
<p>Status Code[200 ~ 400]에 대한 평균 지연</p>
<div id="jira3_chart_div" style="height: 260px;"></div>
<div id="collab_chart_div" style="height: 260px;"></div>
</center>

</body>
</html>