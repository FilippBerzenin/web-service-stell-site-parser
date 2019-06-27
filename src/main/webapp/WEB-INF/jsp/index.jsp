<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix='form' uri='http://www.springframework.org/tags/form'%>
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
<title>Index page</title>
</head>
<body>
	<div class="container">
		<h1>Search metal!!!</h1>
		
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>
		
		<div style="width: 128px; ">
			<a href="/search">New search</a>
		</div>
				<div style="width: 128px; ">
			<a href="/searchPdf">New search from PDF files</a>
		</div>


	</div>
</body>
</html>