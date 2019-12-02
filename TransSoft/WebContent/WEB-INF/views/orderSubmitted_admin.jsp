<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<body>
<a href="/TransSoft/orderRequest_admin">Back to Order Submission</a>
<br>
<p>Order Submission Successful.</p>
<br>
<p>ID: ${id}</p>
<p>Mass: ${mass}</p>
<p>Volume: ${volume}</p>
<p>Transport Date: ${date}</p>
</body>
</html>