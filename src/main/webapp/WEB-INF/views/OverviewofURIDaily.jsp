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

/* #header { */
/* width: 960px; */
/* /* height: 127px; */ */
/* min-height: 44px; */
/* margin: 0 auto; */
/* border-bottom: 1px solid #ccc; */
/* } */

/* #header h2:before { */
/* border: solid #e5e5e5; */
/* border-width: 0 0 0 1px; */
/* content: ""; */
/* float: left; */
/* height: 44px; */
/* margin: 0 12px 0 14px; */
/* } */



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
	height: 300px;
/* 	border: 1px solid black; */
}

.item{
	position: relative;
	height: 300px; width:300px;
/* 	background: blue; */
/* 	height: 300px; */
	float: left;
	display: inline-block;
	margin: 0;
/* 	background: transparent url(www.google.com/images/loading.gif) no-repeat center center; */
}

.item2{
	position: relative;
/* 	height: 300px; width:250px; */
/* 	background: blue; */
/* 	height: 300px; */
	float: left;
	display: inline-block;
	margin: 0;
/* 	background: transparent url(www.google.com/images/loading.gif) no-repeat center center; */
}

</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Overview of URI Daily(Tiger - JIRA Monitoring)</title>

<!--     <link rel="stylesheet" href="//www.google.com/css/maia.css" /> -->


 <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript">

    var isError = "${error}";
    if (isError) {  }
    
    
    // Load the Visualization API and the piechart package.
    google.load('visualization', '1.0', {'packages':['table']});
    google.load('visualization', '1.0', {'packages':['corechart']});
    
    // Set a callback to run when the Google Visualization API is loaded.
    google.setOnLoadCallback(drawChart);

	var sdate = '${dailySearchForm.searchDate}';
	var limitCnt = '${dailySearchForm.limitCnt}';
	var logType = '${dailySearchForm.logType}';
	var service = '${dailySearchForm.service}';
	var baseUrl = location.protocol + "//" + location.host + "/"; 

	
    function drawChart() {
    	
    	if (sdate.length != 10) {
    		return;
    	}
    	
    
    	// 1. HTTP Status Code 통계 차프
    	var httpStatusData = new google.visualization.DataTable();
    	httpStatusData.addColumn('string', 'Status');
    	httpStatusData.addColumn('number', '횟수');
    	httpStatusData.addColumn('number', '%');
    	var httpStatusview = new google.visualization.DataView(httpStatusData);

    	httpStatusview.setColumns([0, 1]);
    	httpStatusview.hideColumns([2]);

    	var httpStatusData4 = new google.visualization.DataTable(); //Http Status Code 중 [200,400)
    	httpStatusData4.addColumn('string', 'Status');
    	httpStatusData4.addColumn('number', '횟수');
	    var normalcnt	= 0;
	    var errorcnt	= 0;

    	
        var httpStatusData2 = new google.visualization.DataTable(); //Http Status Code 중 [200,400)
    	httpStatusData2.addColumn('string', 'Status');
    	httpStatusData2.addColumn('number', '횟수');
    	
        var httpStatusData3 = new google.visualization.DataTable(); //Http Status Code 중 not  [200,400)
    	httpStatusData3.addColumn('string', 'Status');
    	httpStatusData3.addColumn('number', '횟수');    	
        
    	var responseJsonData = $.ajax({
    		type:"GET",
			url: baseUrl + "tiger/rest/daily/httpstatus?date=" + sdate+ "&logType=" + logType + "&service="+service,
			dataType:"json",
			async: false,
			success: responseRatio_json
    	});

    	
    	function responseRatio_json(json){
    
    	    if(json.length == 0 ) {  
    	    	document.getElementById('message_div').innerHTML = "검색된 결과가 없습니다.";
    	    	return;
    	    }
    	    
    	    
    	    var total		= 0;
    	    $.each(json, function(key){
    	    	total += json[key].count;
    	    });
    	    
    	    
    		$.each(json, function(key){
    			
    			httpStatusData.addRow([ json[key].httpStatus, json[key].count, (json[key].count/total)*100 ]);
    			
    			if ( json[key].httpStatus >= 200 && json[key].httpStatus < 400) {
        			httpStatusData2.addRow([ json[key].httpStatus, json[key].count ]);
        			normalcnt	+= json[key].count;

    			} else {
        			httpStatusData3.addRow([ json[key].httpStatus, json[key].count ]);
        			errorcnt	+= json[key].count;
    			}
    		});    		
    	}
    	
    	
		var percentFormatter = new google.visualization.NumberFormat({fractionDigits: 2, suffix: '%'} );
		percentFormatter.format(httpStatusData, 2);

        // Set chart options
        var options = {'title':'HttpStatus(All) 통계',
                       'sliceVisibilityThreshold':0
        };
        // Instantiate and draw our chart, passing in some options.
        var http_chart = new google.visualization.PieChart(document.getElementById('httpStatus'));
//         http_chart.draw(httpStatusData, options);
        http_chart.draw(httpStatusview, options);
        
        //2. Http Status Code 중 200이 아닌 데이터 차트
        var options = {'title':'HttpStatus(Normal) 통계',
                       'sliceVisibilityThreshold':0
        };
        // Instantiate and draw our chart, passing in some options.
        var http2_chart = new google.visualization.PieChart(document.getElementById('httpStatus2'));
        http2_chart.draw(httpStatusData2, options);

        
        //3. Http Status Error Code 차트
        // Instantiate and draw our chart, passing in some options.
        var options = {'title':'HttpStatus(abnormal) 통계',
                       'sliceVisibilityThreshold':0
        };
        var http3_chart = new google.visualization.PieChart(document.getElementById('httpStatus3'));
        http3_chart.draw(httpStatusData3, options);
   
        
        httpStatusData4.addRow(['normal', normalcnt]);
        httpStatusData4.addRow(['abnormal', errorcnt]);
        
        var options = {'title':'HttpStatus(Error) 통계',
                'sliceVisibilityThreshold':0
 		};
        var http4_chart = new  google.visualization.PieChart(document.getElementById('httpStatus4'));
        http4_chart.draw(httpStatusData4, options);
        
        
        //3. Http Status Code 통계표
		var responseRatioTable = new google.visualization.Table(document.getElementById('responseRatioTable'));
		var responseRatioTable_options = {'showRowNumber' : false};
		responseRatioTable_options['title'] = 'HttpStatus 통계';
      
		responseRatioTable.draw(httpStatusData, responseRatioTable_options);

		google.visualization.events.addListener(responseRatioTable, 'select', responseRatioTableSelectHandler);

		function responseRatioTableSelectHandler() {
			var selection = responseRatioTable.getSelection();
			var statusCode = "";
			var statusCnt = "";
// 			var msg = "";
      		for(var i=0; i<selection.length; ++i){
      			var item = selection[i];

      			if(item.row != null){
      				statusCode	= httpStatusData.getFormattedValue(item.row, 0);
      				statusCnt	= httpStatusData.getFormattedValue(item.row, 1);
//      				msg += "row: " + item.row + ", column: " + item.column + " - statusCode: " + statusCode + "\n";
     				console.log( statusCode );
      			}
      		}//for
      		
      		if (statusCode != '200') {
	          	window.open( baseUrl 
// 	          			+ 
// 	          			"tiger/uridetail/view?date="+sdate+"&uripathhash="+uripathhash+"&uripath="+uripath+
// 	          					"&logType=" + logType + "&service="+service
	          					, null);
      		} else {
      			alert("Status가 200이 아닌 것을 클릭해 주세요!");
      		}
		}
		
		
//-----------------------------------------------------------------------------------------------------------------		
		
		//3. 지연시간 별, 총계
		
    	var ResTData = new google.visualization.DataTable();
		ResTData.addColumn('string', '지연시간');
		ResTData.addColumn('number', '총계');

		
    	var responseJsonData = $.ajax({
    		type:"GET",
			url: baseUrl + "tiger/rest/daily/latency?date=" + sdate+ "&logType=" + logType + "&service="+service,
			dataType:"json",
			async: false,
			success: resT_json
    	});
    	
    	function resT_json(json){
    
    	    if(json.length == 0 ) {  
    	    	document.getElementById('message_div').innerHTML = "검색된 결과가 없습니다.";
    	    	return;
    	    }
    	    
    		$.each(json, function(key){
    			ResTData.addRow([ " ~ " + json[key].threshold_max, json[key].count ]);
    		});    		
    	}
    	
    	var options = {
    	          title: 'Response Time 별 총계',
    	          hAxis: {title: '지연시간[㎲]', titleTextStyle: {color: 'green'}},
    	          vAxis: {title: '수'},
    	          legend: 'none',
    	          explorer: {maxZoomOut:4, keepInBounds: true},
    	        };
        var resTchart = new google.visualization.ColumnChart(document.getElementById('resTchart'));
        resTchart.draw(ResTData, options);

//-------------------------------------------------------------------------
//         var ResTData2 = new google.visualization.DataTable();
// 		ResTData2.addColumn('number', '지연시간');
// 		ResTData2.addColumn('number', '총계');

		
//     	var latencyJsonData = $.ajax({
//     		type:"GET",
// 			url: baseUrl + "tiger/rest/daily/latency2?date=" + sdate,
// 			dataType:"json",
// 			async: false,
// 			success: resT_json2
//     	});
    	
//     	function resT_json2(json){
    
//     	    if(json.length == 0 ) {  
//     	    	document.getElementById('message_div').innerHTML = "검색된 결과가 없습니다.";
//     	    	exit;
//     	    }
    	    
//     		$.each(json, function(key){
//     			ResTData2.addRow([ json[key].elaptime , json[key].count ]);
//     		});    		
//     	}
    	
    	
//     	var options2 = {
//     	          title: 'Response Time 별 총계',
//     	          hAxis: {title: '지연시간[ms]', titleTextStyle: {color: 'green'}},
//     	          vAxis: {logScale: true},
//     	          legend: 'none',
//     	        };
//         var resTchart2 = new google.visualization.ScatterChart(document.getElementById('resTchart2'));
//         resTchart2.draw(ResTData2, options2);

//-------------------------------------------------------------------------
//     	// 통계표
		var gData = new google.visualization.DataTable();
		var view = new google.visualization.DataView(gData);
		var arr = {};

    	var jsonData = $.ajax({
			type:"GET",
			url: baseUrl + "tiger/rest/daily/restapisummary?date=" + sdate + "&limitCnt=" + limitCnt+ "&logType=" + logType + "&service="+service,
			dataType:"json",
			async: false,
			success: response_json
    	});

    	
    	function response_json(json){

    	    if(json.length == 0 ) {  
    	    	document.getElementById('message_div').innerHTML = "검색된 결과가 없습니다.";
    	    	return;
    	    }
    		
    		gData.addColumn('string', 'PATH of URI');
    		gData.addColumn('number', '횟수');
    		gData.addColumn('number', '%');
    		gData.addColumn('number', '평균지연[㎲]');
    		gData.addColumn('number', '최소지연[㎲]');
    		gData.addColumn('number', '최대지연[㎲]');
    		gData.addColumn('string', 'uripathhash');

    		
    		view.setColumns([0, 1, 2, 3, 4, 5]);
    		view.hideColumns([6]);
    		


    	    total = 0;
    	    $.each(json, function(key){
    	    	total += json[key].hitCount;
    	    });
    	    
    	    
    	    rowIndex = 0;
    		$.each(json, function(key){
    			
    			gData.addRow( [json[key].uriPath, 
    			               json[key].hitCount,
    			               (json[key].hitCount/total) * 100,
    			               json[key].avgElapTime, 
    			               json[key].minElapTime, 
    			               json[key].maxElapTime, 
    			               json[key].uripath_hash ]);
//     			arr[ rowIndex ] = json[key].uripathHash; 
    		});
    	}
    	
	    

      // Instantiate and draw our chart, passing in some options.
		var table = new google.visualization.Table(document.getElementById('table_div'));
		
		var options = {'showRowNumber' : true};
		options['title'] = 'URI통계';
      
		var formatter = new google.visualization.NumberFormat( {fractionDigits:0} );
		formatter.format(gData, 1);
		formatter.format(gData, 3);
		formatter.format(gData, 4);
		formatter.format(gData, 5);
		
		var percentFormatter = new google.visualization.NumberFormat({fractionDigits: 2, suffix: '%'} );
		percentFormatter.format(gData, 2);

//       var arrowFormat = new google.visualization.ArrowFormat();
//       arrowFormat.format(gData, 2);

		table.draw(view, options);

		
		
		google.visualization.events.addListener(table, 'select', selectHandler);

		function selectHandler() {
			var selection = table.getSelection();
			var uripathhash = "";
			var uripath = "";
			var msg = "";
      		for(var i=0; i<selection.length; ++i){
      			var item = selection[i];

      			if(item.row != null){
      				uripath = gData.getFormattedValue(item.row, 0);
//       				uripathhash = gData.getFormattedValue(item.row, 6);
     				msg += "row: " + item.row + ", column: " + item.column + " - uripath: " + uripath + "\n";
//      				msg += "uripathhash: " + gData.getValue(item.row, 6);
     				console.log( arr[ gData.getValue(item.row, 0)] );
      			}
      		}//for
      		
//       		if (uripathhash.length == 0){
      		if (uripath.length == 0){
      			alert('다시 한번 선택해 주세요!');
      			return;
      		} else {
     		
      			
//       			var param = encodeURIComponent("date="+sdate+"&uripath="+uripath+"&logType=" + logType + "&service="+service);
	          	window.open( baseUrl + 
	          			"tiger/uridetail/view?" + "date="+sdate+"&uripath="+ encodeURIComponent(uripath)+"&logType=" + logType + "&service="+service, null);
      		}
		}
      
    }//drawChar
   

    
    function  viewChart() {

		window.console.log('viewChart');

		google.load("visualization", "1", {packages:["corechart"]});
	     google.setOnLoadCallback(drawChart);
	      function drawChart() {
	        var data = google.visualization.arrayToDataTable([
	          ['Year', 'Sales', 'Expenses'],
	          ['2004',  1000,      400],
	          ['2005',  1170,      460],
	          ['2006',  660,       1120],
	          ['2007',  1030,      540]
	        ]);

	        var options = {
	          title: 'Company Performance',
	          vAxis: {title: 'Year',  titleTextStyle: {color: 'red'}}
	        };

	        var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
	        chart.draw(data, options);
	      }
		  
    }
    
    function changelogType(obj) {
//     	var obj = document.getElementById("selectBox");
    	console.info(obj.selectedIndex);
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


<!-- 	<div class="maia-header" id="maia-header"> -->
<!-- 		<div class="maia-aux"> -->
<!-- 			<h1><a href="/"><img src="//www.lge.com/lg3-common/images/gateway/logo.jpg" alt="LGE"></img></a></h1> -->
<!-- 			<h2>일자별 통계 보고서</h2> -->
<!-- 		</div> -->
<!-- 	</div> -->

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
<h2>일자별 통계</h2>
</center>




<!-- 검색 폼 -->
<form:form method="POST" modelAttribute="dailySearchForm" commandName="dailySearchForm" action="/tiger/daily/">

	<form:errors path="*" cssClass="errorblock" element="div" />
	
	<p>
		<form:label path="searchDate" for="searchDate">검색일자</form:label>
		<form:input path="searchDate" type="text" size="10" placeholder="YYYY-MM-DD" />

		
		<form:label path="limitCnt" for="limitCnt">호출된 최소 횟수</form:label>
		<form:input path="limitCnt" size="5" />
		<form:label path="limitCnt" for="limitCnt">이상</form:label>
		
		
<%-- 		<form:label path="rowCount" for="rowCount">표시할 행 수</form:label> --%>
<%-- 		<form:input path="rowCount" size="5"/> --%>
		
		
		<form:select id="logType" path="logType" for="logType" onchange="changelogType(this);">
			<form:options items="${logTypeList}"></form:options>
		</form:select>
		
		<form:select id="service" path="service" for="service">
			<form:options items='${serviceList}' />
		</form:select>
		
		
		<input type="submit" value="검색" />
	</p>	
</form:form>	

<div id="message_div" style="text-align:center; color:red;"></div>


<div class="container">
	<div id="httpStatus" class="item"></div>
	<div id="httpStatus4" class="item"></div>
	<div id="httpStatus2" class="item"></div>
	<div id="httpStatus3" class="item"></div>
	<div id="responseRatioTable" class="item2"></div>
</div>
	<div id="resTchart" style="width: 600px; height: 400px;"></div>
<br>

<div id="table_div" ></div>


</body>
</html>