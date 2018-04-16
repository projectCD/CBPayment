<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="<c:url value="resources/js/jquery.qrcode.js" />"></script>	
<script src="<c:url value="resources/js/qrcode.js" />"></script>	
<title>CBPay</title>
</head>
<body>
    <!-- 
   <div id="qrcodeTable"></div>
    
    <script>
    jQuery('#qrcodeTable').qrcode({
		render	: "table",
		text	: "http://weixin://wxpay/bizpayurl?pr=sJ5U8KB"
	});	
    </script>
     -->   
 
    
     <form action="genQRCode" method="POST">
     <table>
     	<tr>
     		<td align="center">Account</td>
     		<td><input type="text" name="mch_id" value="500581200000001"></td>
     	</tr>
     	<tr>
     		<td align="center">Product description</td>
     		<td><input type="text" name="body" value=""></td>
     	</tr>
     	<tr>
     		<td align="center">Amount</td>
     		<td><input type="text" name="total_fee" value=""></td>
     	</tr>
     	<tr>
     		<td align="center">Currency</td>
     		<td><input type="text" name="fee_type" value="USD" readonly="readonly" ></td>
     	</tr>
     </table>
    <input type="submit" value="Generate QRCODE">
    </form>

</body>
</html>