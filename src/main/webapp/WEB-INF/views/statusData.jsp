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

<title>데이터 상태(Tiger - JIRA Monitoring)</title>
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
<h2>데이터 보존 상태</h2>



<table>
	<tr>
		<th></th>
		<th colspan=11>TStatus</th>
	</tr>
	<tr>
		<th>일자</th>
		<th>collab1</th>
		<th>crowd</th>
		<th>jira1</th>
		<th>jira2</th>
		<th>jira3</th>
		<th>jira4</th>
		<th>jira5</th>
		<th>jira6</th>
		<th>jira7</th>
		<th>jira_harmony</th>
		<th>jira_tv</th>
	</tr>




<c:if test="${not empty list}">
<%-- 	<c:out value="${list}" default=""></c:out> --%>
	
	<c:forEach var="e" items="${list}">
		<tr>
			<td>${e.ymd}</td>
			
			<c:choose>
			<c:when test="${e.collab1 == 0}"><td bgcolor="red" align="right">${e.collab1}</td></c:when>
			<c:otherwise><td align="right">${e.collab1}</td></c:otherwise>
			</c:choose>
			

			<c:choose>
			<c:when test="${e.crowd == 0}"><td bgcolor="red" align="right">${e.crowd}</td></c:when>
			<c:otherwise><td align="right">${e.crowd}</td></c:otherwise>
			</c:choose>


			<c:choose>
			<c:when test="${e.jira1 == 0}"><td bgcolor="red" align="right">${e.jira1}</td></c:when>
			<c:otherwise><td align="right">${e.jira1}</td></c:otherwise>
			</c:choose>

			<c:choose>
			<c:when test="${e.jira2 == 0}"><td bgcolor="red" align="right">${e.jira2}</td></c:when>
			<c:otherwise><td align="right">${e.jira2}</td></c:otherwise>
			</c:choose>
			
			
			<c:choose>
			<c:when test="${e.jira3 == 0}"><td bgcolor="red" align="right">${e.jira3}</td></c:when>
			<c:otherwise><td align="right">${e.jira3}</td></c:otherwise>
			</c:choose>
			
			
			<c:choose>
			<c:when test="${e.jira4 == 0}"><td bgcolor="red" align="right">${e.jira4}</td></c:when>
			<c:otherwise><td align="right">${e.jira4}</td></c:otherwise>
			</c:choose>
			
			<c:choose>
			<c:when test="${e.jira5 == 0}"><td bgcolor="red" align="right">${e.jira5}</td></c:when>
			<c:otherwise><td align="right">${e.jira5}</td></c:otherwise>
			</c:choose>
			
			<c:choose>
			<c:when test="${e.jira6 == 0}"><td bgcolor="red" align="right">${e.jira6}</td></c:when>
			<c:otherwise><td align="right">${e.jira6}</td></c:otherwise>
			</c:choose>
												

			<c:choose>
			<c:when test="${e.jira7 == 0}"><td bgcolor="red" align="right">${e.jira7}</td></c:when>
			<c:otherwise><td align="right">${e.jira7}</td></c:otherwise>
			</c:choose>
			

			<c:choose>
			<c:when test="${e.jira_harmony == 0}"><td bgcolor="red" align="right">${e.jira_harmony}</td></c:when>
			<c:otherwise><td align="right">${e.jira_harmony}</td></c:otherwise>
			</c:choose>
			
			<c:choose>
			<c:when test="${e.jira_tv == 0}"><td bgcolor="red" align="right">${e.jira_tv}</td></c:when>
			<c:otherwise><td align="right">${e.jira_tv}</td></c:otherwise>
			</c:choose>
									
		</tr>
	</c:forEach>
</c:if>
</table>


</center>



</body>
</html>