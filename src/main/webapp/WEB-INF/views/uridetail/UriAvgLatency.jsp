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

<title>URI 기간 지연(Tiger - JIRA Monitoring)</title>


 <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript">

    var isError = "${error}";
    
    
    // Load the Visualization API and the piechart package.
    google.load('visualization', '1.0', {'packages':['corechart']});

    
    // Set a callback to run when the Google Visualization API is loaded.
    google.setOnLoadCallback(drawChart);

	var sdate = '${uriDetailSearchForm.sdatetime}';
	var edate = '${uriDetailSearchForm.edatetime}';
	var uripath = '${uriDetailSearchForm.uripath}';
	var logType = '${uriDetailSearchForm.logType}';
	var service = '${uriDetailSearchForm.service}';
	
	
	var baseUrl = location.protocol + "//" + location.host + "/"; 

	
    function drawChart() {
    	if (isError) { return; }
    	if (sdate.length != 16) { return; }
    	if (edate.length != 16) { return; }
    	
    	
    	var count = 0;
    	var sumRest = 0;
    	
    	var sumCnt = 0;
    	
    	var uridetailData = new google.visualization.DataTable();
    	uridetailData.addColumn('string', '시각');
    	uridetailData.addColumn('number', '평균지연[㎲]');

    	var countData = new google.visualization.DataTable();
    	countData.addColumn('string', '시각');
    	countData.addColumn('number', '수');		

		
    	var JsonData = $.ajax({
    		type:"GET",
			url: baseUrl + "tiger/rest/uri/averageLatencyForPeriod?sdatetime=" + sdate +
					"&edatetime=" + edate +
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
    			uridetailData.addRow([ json[key].time, json[key].avgLatency ]);
    			countData.addRow( [ json[key].time, json[key].rowcnt ] );
    			sumRest += json[key].avgLatency;
    			sumCnt	+= json[key].rowcnt;
    			count++;
    		});    		
    	}
    	
        // Set chart options
        var options = {'title':'URI 시각 별 평균 지연',
                       legend: 'none',
                       vAxis: { title: '㎲', textStyle: {fontSize:12}},
                       hAxis: { title: '일시' + " (평균:" + parseInt( sumRest/count) + ") " , titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},                       
                       explorer: {maxZoomOut:4, keepInBounds: true},
                       'sliceVisibilityThreshold':0
        };
        var avgRestchart = new google.visualization.AreaChart(document.getElementById('avgRest_div'));
        avgRestchart.draw(uridetailData, options);

        
        var countOptions = {'title':'URI 시각별 평균 횟수',
                legend: 'none',
                hAxis: { textStyle :{fontSize:9}, textPosition : 'none'},
                vAxis: { title: '수', textStyle: {fontSize:12}},
                hAxis: { title: " 총:" + sumCnt +" (평균:" + parseInt( sumCnt/count) + ") " , titleTextStyle: {color: 'green'},  textStyle :{fontSize:10},  minValue: 0},
                colors : ['gray']
 		};

        
        var countchart = new google.visualization.AreaChart(document.getElementById('count_div'));
    	countchart.draw(countData, countOptions);
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
<form:form method="POST" modelAttribute="uriDetailSearchForm" commandName="uriDetailSearchForm" action="/tiger/uridetail/search/">

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
				<form:label path="uripath" for="uripath">uripath</form:label>
			</td>
			<td>
				<form:input type="uripath" path="uripath" size="100" />
			</td>
		</tr>
		<tr>
			<td>
				<form:label path="sdatetime" for="sdatetime">검색 대상 기간</form:label>
			</td>
			<td>
				<form:input path="sdatetime" type="text"  size="18" placeholder='YYYY-MM-DD HH:MI' /> ~
				<form:input  path="edatetime"  type="text" size="18" placeholder='YYYY-MM-DD HH:MI' />
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
<div id="avgRest_div" style="height: 300px;"></div>
<div id="count_div" style="height:200px;" ></div>

</center>

  
</body>
</html>