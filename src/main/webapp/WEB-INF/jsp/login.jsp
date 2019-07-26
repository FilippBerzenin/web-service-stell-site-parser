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
<title>Login page</title>
</head>
<body>
	<div class="container login-container">
		<div class="col-md-6 login-form-1">
			<h3>Login</h3>
			<form action="/login" method="post">
				<div class="form-group">
					<input type="text" class="form-control" name="username" value="" />
				</div>
				<div class="form-group">
					<input type="password" class="form-control" name="password"	value="" />
				</div>
				<div>
					<input type="hidden" name="_csrf" value="${_csrf.token}" />
				</div>
			<div class="form-group">
				<input type="submit" class="btnSubmit" value="Login" />
			</div>
			</form>
		</div>
	</div>
</body>
</html>