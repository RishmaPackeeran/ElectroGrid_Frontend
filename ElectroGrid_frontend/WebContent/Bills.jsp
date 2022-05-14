<%@page import="com.Billing"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset=ISO-8859-1">
	
	<title>Billing Management</title>
	
	<link rel="stylesheet" href="Views/bootstrap.min.css">
	<script src="Components/jquery-3.6.0.min.js"></script>
	<script src="Components/Bills.js"></script>
</head>
<body>

<div class="container"><div class="row"><div class="col-6">

	<h1>Billing Management </h1>

	<form id="formBills" name="formBills">
 		Bill code:
 		<input id="BillCode" name="BillCode" type="text" class="form-control form-control-sm">
 		<br> <br>
 		
 		Bill Month:
		<input id="BillMonth" name="BillMonth" type="text" class="form-control form-control-sm">
 		<br> <br>
 		
 		Current Reading:
 		<input id="CurrentRead" name="CurrentRead" type="text" class="form-control form-control-sm">
 		<br> <br>
 		
 		Previous Reading:
		<input id="PreviousRead" name="PreviousRead" type="text" class="form-control form-control-sm">
 		<br><br>
 		
 		
 		
 		<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
 		<input type="hidden" id="hidBillIDSave" name="hidBillIDSave" value=""><br><br>
	</form>
	
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>

	<br>
	<div id="divBillsGrid">
 		<%
 		Billing billObj = new Billing(); 
 		out.print(billObj.readBills());
 		%>
	</div>
</div> </div> </div>

</body>
</html>