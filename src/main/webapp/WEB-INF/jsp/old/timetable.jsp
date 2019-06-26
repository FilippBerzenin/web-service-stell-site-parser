<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='form' uri='http://www.springframework.org/tags/form' %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/dopstyle.css">
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<c:set var="prefix" value="${pageContext.request.contextPath}" />
<c:set var="page" value="${page}" />
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<a href="${prefix}/">Back</a> <br />
		<div>
			<h2>Search student form:</h2>
			<div class="form-group">
				<form:form method="post" action="${prefix}/timetable/createRequest/student" modelAttribute="entityFor">
					<table>
						<tr>
							<td><font color="red"><form:errors path="name" /></font></td>
							<td><form:input class="form-control" type="text" path="name" value="Fil" placeholder="Enter name"/></td>							
							<td><font color="red"><form:errors path="surename" /></font></td>
							<td><form:input class="form-control" type="text" path="surename" value="Fil" placeholder="Enter surename"/></td>							
							<td><font color="red"><form:errors path="dateStartSearch" /></font></td>
							<td><form:input class="form-control" type="date" path="dateStartSearch" value="01-01-2019" placeholder="Time start (HH-mm)"/></td>							
							<td><font color="red"><form:errors path="dateFinishSearch" /></font></td>
							<td><form:input class="form-control" type="date" path="dateFinishSearch" value="01-01-2020"  placeholder="Time start (HH-mm)"/></td>
							<td><button type="submit">Search ${page}</button></td>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
		<div>
			<h2>Search teacher form:</h2>
			<div class="form-group">
				<form:form method="post" action="${prefix}/timetable/createRequest/teacher" modelAttribute="entityFor">
					<table>
						<tr>
							<td><font color="red"><form:errors path="name" /></font></td>
							<td><form:input class="form-control" type="text" path="name" value="Vika" placeholder="Enter name"/></td>							
							<td><font color="red"><form:errors path="surename" /></font></td>
							<td><form:input class="form-control" type="text" path="surename" value="Berzenin" placeholder="Enter surename"/></td>							
							<td><font color="red"><form:errors path="dateStartSearch" /></font></td>
							<td><form:input class="form-control" type="date" path="dateStartSearch" value="01-01-2019" placeholder="Time start (HH-mm)"/></td>							
							<td><font color="red"><form:errors path="dateFinishSearch" /></font></td>
							<td><form:input class="form-control" type="date" path="dateFinishSearch" value="01-01-2020"  placeholder="Time start (HH-mm)"/></td>
							<td><button type="submit">Search ${page}</button></td>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
		<h1>${page} list</h1>
				<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<table class="table  table-sm">
			<thead class="table-info">
				<tr>
					<th>#</th>
					<th>ID</th>
					<th>Exercise name</th>
					<th>Date</th>
					<th>Begin time</th>
					<th>Finish time</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="entity" items="${listOfEntites}" varStatus="counter">
					<tr>
						<td>${counter.count}</td>
						<td>${entity.id}</td>
						<td>${entity.name}</td>
						<td>${entity.date}</td>
						<td>${entity.timeBegin}</td>
						<td>${entity.timeFinish}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>