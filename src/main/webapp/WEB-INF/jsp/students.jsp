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
<title>Students page</title>
</head>
<body>
	<div class="container">
		<a href="${prefix}/groups/show/all">Back</a> <br />
		<div>
			<h2>Add new student:</h2>
			<div class="form-group">
				<form:form method="post" action="${prefix}/students/create/${group_id}" modelAttribute="studentFor">
					<table>
						<tr>
							<td><font color="red"><form:errors path="name" /></font></td>
							<td><form:input path="name" placeholder="Students name"/></td>
							<td><font color="red"><form:errors path="surename" /></font></td>
							<td><form:input path="surename" placeholder="Students surename"/></td>
							<td><button type="submit">Add new student</button></td>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
		<br />
		<h1>Students list</h1>
				<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<table class="table  table-sm">
			<thead class="table-info">
				<tr>
					<th>#</th>
					<th>ID</th>
					<th>Students name</th>
					<th>Students surename</th>
					<th>Group</th>
					<th>Delete</th>
					<th>Update</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="student" items="${studentsList}" varStatus="counter">
					<tr>
						<td>${counter.count}</td>
						<td>${student.id}</td>
						<td>${student.name}</td>
						<td>${student.surename}</td>
						<td>${student.group.name}</td>
						<td>
							<form action="${prefix}/students/delete/${student.id}" method="post">
								<button type="submit" name="delete" value="Delete">Delete</button>
							</form>
						</td>
						<td>
							<button type="button" class="btn btn-primary dropdown-toggle"
								data-toggle="dropdown">Update</button>
							<div class="dropdown-menu container form-group">

								<form:form class="form-inline" method="post"
									action="${prefix}/students/update/${student.group.id}"
									modelAttribute="studentFor">
									<div class="form-group">
										<form:input type="hidden" path="id" value="${student.id}" />
										<font color="red"><form:errors path="name" /></font>
										<form:input class="form-control" path="name"
											value="${student.name}" />
										<font color="red"><form:errors path="surename" /></font>
										<form:input class="form-control" path="surename"
											value="${student.surename}" />
										<font color="red"><form:errors path="group.name" /></font>
										<form:input class="form-control" path="group.name"
											value="${student.group.name}" />
										<button class="form-control" type="submit">Update</button>
									</div>
								</form:form>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>