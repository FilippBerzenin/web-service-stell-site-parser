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
<title>Exercises page</title>
</head>
<body>
	<div class="container">
		<a href="${prefix}/">Back</a> <br />
		<div>
			<h2>Add new ${page}:</h2>
			<div class="form-group">
				<form:form method="post" action="${prefix}/${page}/create/" modelAttribute="entityFor">
					<table>
						<tr>
							<td><font color="red"><form:errors path="name" /></font></td>
							<td><form:input class="form-control" type="text" path="name" placeholder="${page} name"/></td>
							<td><font color="red"><form:errors path="date" /></font></td>
							<td><form:input class="form-control" type="date" path="date" placeholder="yyyy-mm-dd"/></td>
							<td><font color="red"><form:errors path="timeBegin" /></font></td>
							<td><form:input class="form-control" type="time" path="timeBegin" placeholder="Time start (HH-mm)"/></td>
							<td><font color="red"><form:errors path="timeFinish" /></font></td>
							<td><form:input class="form-control" type="time" path="timeFinish" placeholder="Time finish (HH-mm)"/></td>
							<td><button type="submit">Add new ${page}</button></td>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
		<br />
		<h1>${page} list</h1>
				<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<table class="table  table-sm">
			<thead class="table-info">
				<tr>
					<th>#</th>
					<th>ID</th>
					<th>${page} name</th>
					<th>Date</th>
					<th>Begin time</th>
					<th>Finish time</th>
					<th>Courses list</th>
					<th>Add for course</th>
					<th>Remove course</th>
					<th>Delete</th>
					<th>Update</th>
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
						<td><c:forEach var="courseFromList" items="${entity.courses}">
								<c:out value="${courseFromList.subject}" />
							</c:forEach>
						<td>
							<button type="button" class="btn btn-primary dropdown-toggle"
								data-toggle="dropdown">Add new course</button>
							<div class="dropdown-menu container form-group">
								<form:form class="form-inline" method="POST"
									action="${prefix}/${page}/addCourse/${entity.id}"
									modelAttribute="course">
									<div class="form-group">
										<table>
											<tr>
												<td><form:label path="subject">Add course</form:label></td>
												<td><form:input path="subject" /></td>
											</tr>
											<tr>
												<td><input type="submit" value="Add course" /></td>
											</tr>
										</table>
									</div>
								</form:form>
							</div>
						</td>
						<td>
							<button type="button" class="btn btn-primary dropdown-toggle"
								data-toggle="dropdown">Remove course</button>
							<div class="dropdown-menu container form-group">
								<form:form class="form-inline" method="POST"
									action="${prefix}/${page}/removeCourse/${entity.id}"
									modelAttribute="course">
									<div class="form-group">
										<table>
											<tr>
												<td><form:label path="subject">Remove course</form:label></td>
												<td><form:input path="subject" /></td>
											</tr>
											<tr>
												<td><input type="submit" value="Remove course" /></td>
											</tr>
										</table>
									</div>
								</form:form>
							</div>
						</td>
						<td>
							<form action="${prefix}/${page}/delete/${entity.id}"
								method='post'>
								<button type="submit" name="delete" value="Delete">Delete</button>
							</form>
						<td>
							<button type="button" class="btn btn-primary dropdown-toggle"
								data-toggle="dropdown">Update</button>
							<div class="dropdown-menu container form-group">
								<form:form class="form-inline" method="post"
									action="${prefix}/${page}/update/" modelAttribute="entityFor">
									<div class="form-group">
										<form:input class="form-control" type="hidden" path="id"
											value="${entity.id}" />
										<font color="red"><form:errors path="name" /></font>
										<form:input class="form-control" type="text" path="name"
											placeholder="${page} name" value="${entity.name}" />
										<font color="red"><form:errors path="date" /></font>
										<form:input class="form-control" type="date" path="date"
											placeholder="dd-mm-yyyy" value="${entity.date}" />
										<font color="red"><form:errors path="timeBegin" /></font>
										<form:input class="form-control" type="time" path="timeBegin"
											value="${entity.timeBegin}" />
										<font color="red"><form:errors path="timeFinish" /></font>
										<form:input class="form-control" type="time" path="timeFinish"
											value="${entity.timeFinish}" />
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