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
<title>Hosts page</title>
</head>
<body>
	<div class="container">
		<div>
			<a href="<c:url value="/logout" />">Logout</a>
		</div>
		<div>
			<a href="/linksForSearch">Multi searching from links</a>
		</div>
		<div>
			<c:if test="${not empty message}">
				<div class="alert alert-success">${message}</div>
			</c:if>
			<div class="form-group">
				<h2>Add new link:</h2>
				<form:form method="post" action="${prefix}/${page}/create/"
					modelAttribute="entityFor">
					<table>
						<tr>
							<td><font color="red"><form:errors
										path="urlForResource" /></font></td>
							<td><form:input class="form-control" path="urlForResource"
									placeholder="URL for resource" /></td>
							<td><button type="submit">Add new link</button></td>
						</tr>
					</table>
					<div>
						<input type="hidden" name="_csrf" value="${_csrf.token}" />
					</div>
				</form:form>
			</div>
			<div class="form-group">
				<h3>Add new host:</h3>
				<form class="form-group" method="post"
					action="${prefix}/${page}/addHost">
					<table>
						<tr>
							<td><input type="text" name="host" /></td>
							<td><button type="submit">Add new host</button></td>
						</tr>
					</table>
					<div>
						<input type="hidden" name="_csrf" value="${_csrf.token}" />
					</div>
				</form>
			</div>
			<div class="form-group">
				<h3>File download:</h3>
				Select a file to upload: <br />
				<form class="form-group" action="${prefix}/${page}/addPdfFile"
					method="post" enctype="multipart/form-data">
					<input type="file" name="file" size="50" />
					<button type="submit">Upload File</button>
					<div>
						<input type="hidden" name="_csrf" value="${_csrf.token}" />
					</div>
				</form>
			</div>
		</div>
		<br />
		<h1>${page} list</h1>
			<div>
			Page number: 
			<c:forEach var="number_page" items="${countPage}">
			<td><a href="${prefix}/${page}/show/${number_page}">${number_page}</a></td>
			</c:forEach>
			</div>
		<table class="table  table-sm">
			<thead class="table-info">
				<tr>
					<th>#</th>
					<th>ID</th>
					<th>${page}name</th>
					<th>Link list</th>
					<th>Resources type</th>
					<th>Updates</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="entity" items="${listOfEntites}" varStatus="counter">
					<tr>
						<td>${counter.count}</td>
						<td>${entity.id}</td>
						<td><a href="${entity.host}"></a>${entity.host}</td>
						
						<c:choose>
								<c:when test="${entity.resourcesType == 'HOST_RESOURCE'}">
									 <td>${entity.resourcesType}</td>
								</c:when>
								<c:otherwise>
									 <td><a href="${entity.urlForResource}"></a>${entity.urlForResource}</td>
								</c:otherwise>
							</c:choose>	
						
						<td>${entity.resourcesType}</td>
						<td>
							<form action="${prefix}/${page}/upload/${entity.id}"
								method="post">
								<button type="submit" name="upload" value="Upload">Upload</button>
								<div>
									<input type="hidden" name="_csrf" value="${_csrf.token}" />
								</div>
							</form>
						</td>
						<td>
							<form action="${prefix}/${page}/delete/${entity.id}"
								method="post">
								<button type="submit" name="delete" value="Delete">Delete</button>
								<div>
									<input type="hidden" name="_csrf" value="${_csrf.token}" />
								</div>
							</form>
						</td>
					</tr>
				</c:forEach>
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