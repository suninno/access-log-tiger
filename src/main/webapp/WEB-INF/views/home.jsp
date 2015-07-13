<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Collab and JIRA Monitoring Systems</title>
	
	
<!-- <script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.js"></script> -->


<style type="text/css">

div#footer {
 	text-align:center;
    width: 100%;
    height: 50px;
    position: fixed;
    bottom: 0px;
    left: 0px;
}

/* #foot{ */

/* 	clear: both; */
/* 	background-color: #8B64E1; */
/* 	width:100%; */
/* 	height: 50px; */
/* 	border: #666 solid 1px; */
/* 	padding-top: 20px; */
/* } */
</style>

</head>
<body>

<table width="100%">
	<tr>
		<td>
			<div align="right" style="font-size:small; color:dimgray;">서버 시각：${serverTime}</div>
		</td>
	</tr>
	<tr>
		<td>
			<div align="right" style="font-size:small; color:dimgray;">Version：<spring:eval expression="@propertyConfigurer['config.version']" /></div>
		</td>
	</tr>
</table>


<center>
<h1>Collab and JIRA Monitoring Systems</h1>
</center>

<%-- <P>  The time on the server is ${serverTime}. </P> --%>

<a href="/tiger/real/">실시간 모니터링</a> <br />
<a href="/tiger/daily/">일자별 통계</a> <br />
<a href="/tiger/latency/">지연별 통계</a> <br />
<a href="/tiger/dur-latency/">서비스별 기간 지연 정보</a> <br />
<a href="/tiger/uridetail/search/">URI 기간 지연</a> <br />
<a href="/tiger/uridetail/history/">URI 이력 비교</a> <br />
<a href="/tiger/srcip/">Source IP별 통계</a> <br />
<a href="/tiger/clienttime/">ClientTime 시간별 통계</a> <br />
<a href="/tiger/clienttime/daily/">ClientTime 일별 통계</a> <br />
<a href="/tiger/status/data/">데이터 보존 상태</a> <br />

<div>

일단위 집계에서의 제외 대상
	일 100회 미만호출 & 최대 지연이 1초 미만인 URI

분단위 집계에서의 제외 대상
	일 100회 미만호출 & 최대 지연이 1초 미만인 URI
	다음 URI 
  	/main/rest/mywork/latest/status/notification/count
	/main/json/startheartbeatactivity.action
  	/crowd/rest/usermanagement/1/config/cookie
</div>



<div id="footer">
	Software Infra Team,
	Software Capability Development Center,
	CTO Department
	LG Electronics
</div>

</body>
</html>
