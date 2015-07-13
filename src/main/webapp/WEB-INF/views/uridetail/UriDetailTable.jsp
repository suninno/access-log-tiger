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

.center
{
	margin-left:auto;
	margin-right:auto;
/* 	width:70%; */
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>URI 평균 지연 상세(Tiger - JIRA Monitoring)</title>

 <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript">

    google.load('visualization', '1.0', {'packages':['table']});
    google.setOnLoadCallback(drawTable);
    

	var datetime	= '${datetime}';
	var sdatetime	= '${sdatetime}';
	var edatetime	= '${edatetime}';
	var uripath		= "${uripath}";
	var logType		= "${logType}";
	var service		= "${service}";
	var baseUrl = location.protocol + "//" + location.host + "/tiger/rest"; 


	//---------------------------------------------------------
	// 통계표
	
	function drawTable() {
		var gData = new google.visualization.DataTable();

    	var jsonData = $.ajax({
			type:"GET",
			url: baseUrl + "/uri/averageLatencyForPeriod?sdatetime=" + sdatetime +"&edatetime="+ edatetime+ "&uripath="+ uripath+"&logType="+logType+"&service="+service,
			dataType:"json",
			async: false,
			success: response_json
    	});

    	
    	function response_json(json){

    		gData.addColumn('string', '시각');
    		gData.addColumn('string', 'params');
    		gData.addColumn('number', 'httpStatus');
    		gData.addColumn('number', 'Tx');
    	    gData.addColumn('number', 'Latency[㎲]');
    	    gData.addColumn('string', 'srcIP');
    	    gData.addColumn('string', 'requestID');
    	    gData.addColumn('string', 'UserID');
    	    
    		
    	    if(json.length == 0 ) {  
    	    	document.getElementById('message_div').innerHTML = "검색된 결과가 없습니다.";
    	    	return;
    	    }

    	    
    	    rowIndex = 0;
    		$.each(json, function(key){

    			console.info(json[key].sentBytes);
    			gData.addRow(
    					[
    					 json[key].calledTime,
    					 json[key].uriParams,
    					 json[key].httpstatus,
    					 json[key].sentBytes,
    					 json[key].latency,
    					 json[key].srcIP,
    					 json[key].requestID,
    					 json[key].userID,
    					 ]);
    		});
    	}//response_json
    	
	    

		var table = new google.visualization.Table(document.getElementById('table_div'));
		
		var options = {'showRowNumber' : true};
		options['title'] = 'URI통계';
      
		var formatter = new google.visualization.NumberFormat( {fractionDigits:0} );
		formatter.format(gData, 2);
		formatter.format(gData, 3);
		formatter.format(gData, 4);
		

		table.draw(gData, options);
	}//drawTable
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
		<h3>URI 평균 지연 상세</h3>
		<h5>시각: ${sdatetime} ~ ${edatetime}</h5>
		<h5>uripath: ${uripath}</h5>
		<h5>logType: ${logType}</h5>
		<h5>Service: ${service}</h5>
		
<div style="font-size:small; font-style: italic;">
일자별 URI 평균 지연 페이지의 확대 그래프를 클릭하면, 표시된다.<br/>
<font color="red">DB부하 관계로 초 단위 데이터는 제공하고 있지 않다.</font>
</div>	


<div id="message_div" style="text-align:center; color:red;"></div>
</center>
<div id="table_div"></div>


  
</body>
</html>