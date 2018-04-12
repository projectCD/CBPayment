<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HelloWorld page</title>
</head>
<body>
    Welcome to CBpayments
    <form action="helloagain" method="get">
    <input type= "text"  name="name">
    <input type="submit">
    </form>
    <form action="goGoogle" method="get">
    <input type= "text"  name="name" value="togoogle">
    <input type="submit">
    </form>
    
    
    	String mch_id = request.getParameter("mch_id");
    	String service = request.getParameter("service");
    	String body = request.getParameter("body");
    
     <form action="xmlSubmit" method="get">
   	 mch_id<input type="text" name="mch_id" value="500581200000001"><br>
   	 body<input type="text" name="body" value=""><br>
   	 total_fee<input type="text" name="total_fee" value=""><br>
    <input type="submit">
    </form>

</body>
</html>