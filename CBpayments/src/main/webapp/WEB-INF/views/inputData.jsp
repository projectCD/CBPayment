<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="<c:url value="resources/js/Ajax.js" />"></script>
<script type="text/javascript">
	
</script>
<title>inputData page</title>
</head>
<body>
	your name :
	<c:out value="${name}"></c:out>
	<br>
	<input type="button" value="get data json" onclick="searchAjax()">
	<table id="JSON_table"></table>
</body>
</html>