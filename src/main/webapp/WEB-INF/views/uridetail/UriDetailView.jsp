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

<title>일자별 URI 평균 지연(Tiger - JIRA Monitoring)</title>

 <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript">

    google.load('visualization', '1.0', {'packages':['corechart']});


	var sdate	= '${date}';
	var uripath = "${uripath}";
	var logType = "${logType}";
	var service = "${service}";
	var baseUrl = location.protocol + "//" + location.host + "/"; 

    // 평균 지연 chart options
    var avgResTOptions = {'title':'URI 시각별 평균 지연',
                   legend: 'none',
                   hAxis: { textStyle :{fontSize:9}},
                   vAxis: { title: '㎲', textStyle: {fontSize:12}},
    };
    
    var countOptions = {'title':'URI 시각별 평균 횟수',
                   legend: 'none',
                   hAxis: { textStyle :{fontSize:9}, textPosition : 'none'},
                   vAxis: { title: '수', textStyle: {fontSize:12}},
                   colors : ['gray']
    };
    
    google.setOnLoadCallback(drawChart);
    
    
    function drawChart() {
        var avgResTchart	= new google.visualization.AreaChart(document.getElementById('avgRest_div'));
        var countchart		= new google.visualization.AreaChart(document.getElementById('count_div'));
        var childchart 		= new google.visualization.LineChart(document.getElementById('child_div'));
        
    	var avgRestData = new google.visualization.DataTable();
    	avgRestData.addColumn('string', '시각');
    	avgRestData.addColumn('number', '평균지연[㎲]');
    	avgRestData.addColumn({type: 'string', role: 'tooltip'});

		var countData = new google.visualization.DataTable();
    	countData.addColumn('string', '시각');
    	countData.addColumn('number', '수');		
    	
    	console.info(uripath);
    	
    	var JsonData = $.ajax({
    		type:"GET",
			url: baseUrl + "tiger/rest/uri/avgRest?date=" + sdate + "&uripath="+ encodeURIComponent(uripath)
					+"&logType="+logType+"&service="+service,
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
    			avgRestData.addRow([ json[key].calledTime, json[key].avgRest,
    			                     '시각:' + json[key].calledTime + 
    			                     ' , 평균지연:' + json[key].avgRest + '[㎲]' +
    			                     ' , 횟수:' + json[key].count
    			                     ]);
    			countData.addRow([json[key].calledTime, json[key].count]);
    		});    		
    	}


    	
    	//이벤트 부여
    	google.visualization.events.addListener(avgResTchart, 'select', function(){
    		var selectedItem = avgResTchart.getSelection()[0];
    		
    		if (selectedItem) {
    			magnifyData(avgRestData.getValue(selectedItem.row, 0), avgRestData);
    		}
    	});
    	
    	
    	avgResTchart.draw(avgRestData, avgResTOptions);
    	countchart.draw(countData, countOptions);
        
    }//drawChar
    
    
    // 확대 그래프 보이기
    function magnifyData(standardTime, oriData) {
    	
    	var start = new Date(sdate +" "+ standardTime);
    	start.setHours( start.getHours() -1);
    	
    	var end = new Date(sdate +" "+ standardTime);
    	end.setHours(end.getHours() + 1);
    	
    	var newData = new google.visualization.DataTable();
    	newData.addColumn('string', '시각[㎲]');
    	newData.addColumn('number', '평균지연');
    	newData.addColumn({type: 'string', role: 'tooltip'});

    	for(var i=0; i < oriData.getNumberOfRows(); i++){
			if ( oriData.getValue(i, 0) >= start.toHHMMSS() &&  oriData.getValue(i, 0) <= end.toHHMMSS()) {
				newData.addRow( [oriData.getValue(i,0), oriData.getValue(i,1),
				                 oriData.getValue(i, 2)
				                 ]);
			}
    	}
        
		var options_magnify = {
				'title':'확대그래프',
				titleTextStyle : {color: 'green'},
                   legend: 'none',
                   vAxis: { title: '㎲', minValue: 0.0, textStyle :{fontSize:10, bold: true }},
                   hAxis: { textStyle :{fontSize:9},},
			animation:{
						duration: 1000,
						easing: 'out'
						}            
		};
        var childchart = new google.visualization.LineChart(document.getElementById('child_div'));
        
        
    	//이벤트 부여
    	google.visualization.events.addListener(childchart, 'select', function(){
    		var selectedItem = childchart.getSelection()[0];    		
    		
    		var datetime = sdate + " " + newData.getValue(selectedItem.row, 0);
    		
          	window.open( baseUrl + "tiger/uridetail/table?datetime="+datetime+"&uripath="+uripath+
            					"&logType=" + logType + "&service="+service, null);    		

    	});
    	
        childchart.draw(newData, options_magnify);
    }
   
    
    Date.prototype.toHHMMSS = function() {
    	var hh = this.getHours().toString();
    	var mm = this.getMinutes().toString();
    	var ss = this.getSeconds().toString();
    	
    	return (hh[1]?hh:"0"+hh[0]) +":"+(mm[1]?mm:"0"+mm[0])+":"+(ss[1]?ss:"0"+ss[0]);
	};
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
		<h3>일자별 URI 평균 지연</h3>
		<h6>일자: ${date}, uripath: ${uripath}</h6>
<div style="font-size:small; font-style: italic;">
일자별 통계 URI 상세표를 클릭하면, 표시되는 화면이다. <br/> "URI 시각별 평균 지연" 그래프를 클릭하면, 하단에 확대 그래프가 표시된다.
또한, 그 확대 그래프를 클릭하면, 더욱 상세한 표가 표시되지만, <font color="red">DB부하 관계로 초 단위 데이터는 제공하고 있지 않다.</font>
</div>	
		
		
<div id="message_div" style="text-align:center; color:red;"></div>
<div id="avgRest_div" style="height:250px;" ></div>
<div id="count_div" style="height:150px;" ></div>
<div id="child_div" style="height:200px;"></div>
</center>


  
</body>
</html>