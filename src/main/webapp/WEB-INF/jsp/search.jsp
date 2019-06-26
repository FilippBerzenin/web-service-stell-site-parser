<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/dopstyle.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<c:set var="prefix" value="${pageContext.request.contextPath}" />
<c:set var="page" value="${page}" />

<c:url var="actionUrl" value="/search" />
<title>Search page</title>
</head>
<body>
	<br>
	<br>
	<h1>Search page</h1>
	<form:form action="${actionUrl}" modelAttribute="stell" method="POST"
		acceptCharset="UTF-8">
		<table>
			<tr>
			<td><form:label path="link">Link</form:label></td>
				<td><form:input path="link" placeholder="link"/></td>
				<td><font color="red"><form:errors path="link"/></font></td>
			</tr>
			<tr>
				<td><form:label path="brand">Brand</form:label></td>
				<td><form:input path="brand" placeholder="brand"/></td>
				<td><font color="red"><form:errors path="brand" /></font></td>
			</tr>
			<tr>
				<td><form:label path="alloy">Alloy</form:label></td>
				<td><form:input path="alloy" placeholder="alloy"/></td>
				<td><font color="red"><form:errors path="alloy" /></font></td>
			</tr>
			<tr>
				<td><form:label path="typeProduction">Product type</form:label></td>
				<td><form:input path="typeProduction" placeholder="typeProduction"/></td>
				<td><font color="red"><form:errors path="typeProduction" /></font></td>
			</tr>
			<tr>
				<td><form:label path="amount">Amount</form:label></td>
				<td><form:input path="amount" placeholder="amount"/></td>
				<td><font color="red"><form:errors path="amount" /></font></td>
			</tr>
			<tr>
				<td><form:label path="size">Size</form:label></td>
				<td><form:input path="size" placeholder="size"/></td>
				<td><font color="red"><form:errors path="size" /></font></td>
			</tr>
			<tr>
				<td><form:label path="standart">Standart</form:label></td>
				<td><form:input path="standart" placeholder="standart"/></td>
				<td><font color="red"><form:errors path="standart" /></font></td>
			</tr>
			<tr>
				<td><form:label path="performative">Performative</form:label></td>
				<td><form:input path="performative" placeholder="performative"/></td>
				<td><font color="red"><form:errors path="performative" /></font></td>
			</tr>
		</table>
		<form:button id="add-search">Add search</form:button>
	</form:form>
	
			<h1>Result list</h1>
				<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<table class="table  table-sm">
			<thead class="table-info">
				<tr>
					<th>#</th>
					<th>Link</th>
					<th>brand</th>
					<th>alloy</th>
					<th>typeProduction</th>
					<th>amount</th>
					<th>size</th>
					<th>standart</th>
					<th>performative</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="stell" items="${tellsResult}" varStatus="counter">
					<tr>
						<td>${counter.count}</td>
						<td>${stell.link}</td>
						<td>${stell.brand}</td>
						<td>${stell.alloy}</td>
						<td>${stell.typeProduction}</td>
						<td>${stell.amount}</td>
						<td>${stell.size}</td>
						<td>${stell.standart}</td>
						<td>${stell.performative}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</html>