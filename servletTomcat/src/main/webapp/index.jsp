<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ServletTest</title>
</head>
    <body>
    <div>
        <h3>Have a good day =)</h3>
        <h3>Current request</h3>
        <b>IP: ${currentRequest.ip}</b><br>
        <b>User-Agent: ${currentRequest.userAgentHead}</b><br>
        <p>Request date and time: ${currentRequest.requestTime}</p><br>
    </div>
    <c:if test="${!repository.isEmpty()}">
    <div>
    <h3>Request list</h3>
    </div>
    </c:if>
    <c:forEach var="requestInfo" items="${repository}">
    <div>
        IP address: ${requestInfo.ip}<br>
        User-Agent: ${requestInfo.userAgentHead}<br>
        Request Date & Time: ${requestInfo.requestTime}<br><br>
    </div>
    </c:forEach>
    </body>
</html>