<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="<c:url value="resources/js/jquery.qrcode.js" />"></script>	
<script src="<c:url value="resources/js/qrcode.js" />"></script>
<script src="<c:url value="resources/js/Ajax.js" />"></script>
<script type="text/javascript">
	
</script>
<title>QR Code</title>
</head>
<body>


	<font color="red"> <c:out value="${errorMsg}"></c:out></font>
	<div id="qrcodeTable"></div>
	<script>
	
	$(function(){
		var Qrcode = $("#qrCode").val();
		if(typeof Qrcode!= "undefined" && Qrcode!=null && Qrcode.replace(/(^s*)|(s*$)/g, "").length !=0){
		    jQuery('#qrcodeTable').qrcode({
				render	: "table",
				//text	: "http://"+Qrcode
				text	: Qrcode
			});	
		}
	});

    </script>
	<!-- 
	 your name :
	<c:out value="${name}"></c:out>
	<br>
	<input type="button" value="get data json" onclick="searchAjax()">
	<input type="button" value="get data json" onclick="searchAjaxTest()">
	<input type="text" id="xmlName">
	<table id="JSON_table">
	</table>
	<c:out value="${url}"></c:out>
	 -->
	 <input type="hidden" id = "qrCode" value ="${url}"/>
	
</body>
</html>