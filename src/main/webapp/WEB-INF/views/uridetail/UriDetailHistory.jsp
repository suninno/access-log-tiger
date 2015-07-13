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
<style>
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
	height: 300px;
	border: 1px solid black;
}

.item{
	position: relative;
/* 	height: 30px; width:100px; */
/* 	background: blue; */
/* 	height: 300px; */
	float: left;
	display: inline-block;
	margin: 0;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>URI 이력 비교(Tiger - JIRA Monitoring)</title>


 <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript">

    var isError = "${error}";
    
    
    // Load the Visualization API and the piechart package.
    google.load('visualization', '1.0', {'packages':['corechart']});

    
    // Set a callback to run when the Google Visualization API is loaded.
    google.setOnLoadCallback(drawChart);

	var sdate = '${uriDetailHistoryForm.searchdate}';
	var uripath = '${uriDetailHistoryForm.uripath}';
	var logType = '${uriDetailHistoryForm.logType}';
	var service = '${uriDetailHistoryForm.service}';
	
	
	var baseUrl = location.protocol + "//" + location.host + "/"; 

	
    function drawChart() {
    	if (isError) { return; }
    	if (sdate.length != 10) { return; }
    	
    	
    	var uridetailData = new google.visualization.DataTable();
    	uridetailData.addColumn('string', '시각[ms]');
    	uridetailData.addColumn('number', 'WeekAgo');
    	uridetailData.addColumn('number', 'PrevDay');
    	uridetailData.addColumn('number', 'Target');


    	
    	var cntData = new google.visualization.DataTable();
    	cntData.addColumn('string', '시각[ms]');
    	cntData.addColumn('number', 'WeekAgo');
    	cntData.addColumn('number', 'PrevDay');
    	cntData.addColumn('number', 'Target');
    	
    	
    	var count = 0;
    	var targetsum = 0;
    	var yestersum = 0;
    	var aweekagosum = 0;
    	
    	var targetrow = 0;
    	var yesterrow = 0;
    	var aweekagorow = 0;
    	
		
    	var JsonData = $.ajax({
    		type:"GET",
			url: baseUrl + "tiger/rest/uri/urihistory?searchdate=" + sdate +
					"&uripath="+ uripath +
					"&logType="+ logType + "&service=" + service,
			dataType:"json",
			async: false,
			success: res_json
    	});
    	
    	function res_json(json){
    
    	    if(json.length == 0 ) {  
    	    	document.getElementById('message_div').innerHTML = "검색된 결과가 없습니다.";
    	    	return;
    	    }


    		$.each(json, function(key){
    			uridetailData.addRow([ json[key].minute, json[key].aWeekAgoAvg, json[key].yesterDayAvg, json[key].curAvg  ]);
    			
    			cntData.addRow( [json[key].minute,  json[key].aWeekAgoRowCnt, json[key].yesterDayRowCnt, json[key].curRowCnt] );
    			
				targetsum += json[key].curAvg;
				yestersum += json[key].yesterDayAvg;
				aweekagosum += json[key].aWeekAgoAvg;
				
				targetrow += json[key].curRowCnt;
				yesterrow	+= json[key].yesterDayRowCnt;
				aweekagorow	+= json[key].aWeekAgoRowCnt;
    			count++;
    		});    		
    	}
    	
        // Set chart options
        var options = {'title':'URI 시각 별 평균 지연[ms]',
                       vAxis: { title: '㎲', textStyle: {fontSize:12}},
                       hAxis: { title: '일시' + " (평균 Target:" + parseInt( targetsum/count) +", PrevDay: " + parseInt(yestersum/count) + ", aWeekAgo:"+ parseInt(aweekagosum/count) +") " , titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
                       
                       explorer: {maxZoomOut:8, keepInBounds: true},
                       'sliceVisibilityThreshold':0
        };
        var chart = new google.visualization.LineChart(document.getElementById('avgRest'));
        chart.draw(uridetailData, options);
        

        
        var cntoptions = {'title':'URI 시각 별 호출횟수',
             vAxis: { title: '㎲', textStyle: {fontSize:12}},
             hAxis: { title: '일시' + " (평균 Target:" + parseInt( targetrow/count) +", PrevDay: " + parseInt(yesterrow/count) + ", aWeekAgo:"+ parseInt(aweekagorow/count) +") " , titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
             explorer: {maxZoomOut:8, keepInBounds: true},
             'sliceVisibilityThreshold':0
			};
        var cnt = new google.visualization.LineChart(document.getElementById('cntDiv'));
        cnt.draw(cntData, cntoptions);

    }//drawChar
   
    
    
    
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



<!-- commandName="uriDetailSearchForm" -->
<form:form method="POST" modelAttribute="uriDetailHistoryForm" commandName="uriDetailHistoryForm" action="/tiger/uridetail/history/">

	<form:errors path="*" cssClass="errorblock" element="div" />
	
	<table>
	
		<tr>
		<td>
		app:
		<form:select id="logType" path="logType" for="logType" onchange="changelogType(this);">
			<form:options items="${logTypeList}"></form:options>
		</form:select>
		</td>
		
		<td>
		service:
		<form:select id="service" path="service" for="service">
			<form:options items='${serviceList}' />
		</form:select>
		</td>
		</tr>
		
		<tr>
			<td>
				<form:label path="searchdate" for="searchdate">검색일자</form:label>
			</td>
			<td>
				<form:input path="searchdate" type="text"  size="10" placeholder='YYYY-MM-DD' />
			</td>
		</tr>

		<tr>
			<td>
				<form:label path="uripath" for="uripath">uripath</form:label>
			</td>
			<td>
				<form:input type="uripath" path="uripath" size="100" />
			</td>
		</tr>


		
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="검색" />
			</td>
		</tr>
	</table>
</form:form>


<div id="message_div" style="text-align:center; color:red;"></div>
<div id="avgRest" style="height: 400px;"></div>
<div id="cntDiv" style="height: 400px;"></div>
</center>

  
</body>
</html>