<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<c:set var="prefix" value="${pageContext.request.contextPath}" />
<c:set var="page" value="${page}" />
<title>Links page</title>
</head>
<body>
	<div class="container">
		<div>
			<a href="<c:url value="/logout" />">Logout</a>
		</div>
		<div>
			<a href="/linksForResources/show/all">Links page</a>
		</div>
		<h1>Hello searcher!</h1>
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		<form action="/linksForSearch"" method="GET">
		<p><button type="submit">Reset</button></p>
						<div>
					<input type="hidden" name="_csrf" value="${_csrf.token}" />
				</div>
		</form>
		<c:if test="${not empty result}">
		<table class="table  table-sm">
			<thead class="table-info">
				<tr align="center">
					<th>#</th>
					<th>Host</th>
					<th>Number of coincidences</th>
					<th>Line</th>
					<th>Number line</th>
					<th>Metal type</th>
					<th>Words in line</th>
					<th>Sources</th>
				</tr>
			</thead>
			<tbody>

					<c:forEach var="res" items="${result}"
						varStatus="counter">
						<tr>

							<td>${counter.count}</td>
							<td><a href="${res.host}" target="_blank">${res.host}</a></td>
							<td>${res.countEquals}</td>
							<td>${res.line}</td>
							<td>${res.numberOfLine}</td>
							<td>${res.metalType}</td>
							<td>${res.keywords}</td>
							<td><a href="${res.link}" target="_blank">Go to source</a></td>
							
						</tr>
					</c:forEach>
			</tbody>
		</table>
		</c:if>
		
		
		<table class="table  table-sm">
			<thead class="table-info">
				<tr>
					<th>Checkout</th>
					<th>#</th>
					<th>ID</th>
					<th>Host</th>
					<th>Link</th>
				</tr>
			</thead>
			<tbody>
				<form:form method="POST" action="${prefix}/${page}/multiSerching"
					modelAttribute="links">
					<div class="form-group">
						<label for="metalType">Enter metal type</label>
						<form:input class="form-control" path="metalType"/>
					</div>
						<td><font color="red"><form:errors path="metalType" /></font></td>
					<div class="form-group">
						<label for="keywords">Enter keywords, for example X120Mn12 1.3401 rund</label>
						<form:input class="form-control" path="keywords"  placeholder="X120Mn12 1.3401 rund"/>
					</div>
						<td><font color="red">
						<form:errors path="keywords" /></font></td>
						<p><button type="submit">Search</button></p>
					<c:forEach var="entity" items="${listOfEntites}"
						varStatus="counter">
						<tr>
							<td><form:checkbox path="linksFor"
									value="${entity.localPathForTxtFile}" checked="checked"/></td>
							<td>${counter.count}</td>
							<td>${entity.id}</td>
							<td><a href="${entity.host}"/>${entity.host}</td>
							<td><a href="${entity.urlForResource}"/>${entity.urlForResource}</td>
						</tr>
					</c:forEach>
					<div>
						<input type="hidden" name="_csrf" value="${_csrf.token}" />
					</div>
				</form:form>
			</tbody>
		</table>
	</div>
			<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
		integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
		crossorigin="anonymous"></script>
</body>
</html>