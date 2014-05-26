<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cas" uri="/cas.tld" %>
<!doctype html>
<html lang="fr_FR">
<head>
	<meta charset="UTF-8">
	<title>Révocation interface</title>
	<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

	<header class="container"> 

		<h1>Ticket Granting Ticket revocation interface</h1>
		
	</header>


	<section class="container">

		<c:if test="${not empty user}">
			<c:choose>
				<c:when test="${destroyedTicket gt 0}">
					<p class="bg-success">
						You successfully deleted ${destroyedTicket} Ticket Granting Ticket(s) for user <strong>${user}</strong>.
						<br>
						He does not have active sessions anymore.
					</p>
				</c:when>
				<c:when test="${destroyedTicket eq 0}">
					<p class="bg-warning">
						You requested to delete all Ticket Granting Ticket for <strong>${user}</strong>, but he does not have any active session.
					</p>
				</c:when>
			</c:choose>
		</c:if>
		
		<form action="./admin" method="post" role="form">
			<div class="form-group">
				<label for="field-username">Username : </label>
				<input type="text" name="user" id="field-username" required>
			</div>
			<button type="submit" class="btn btn-default">
				Submit
			</button>
		</form>
	
	</section>

	<footer class="container">
		
		<hr>
		<p>Cas webapp admin - Université de La Rochelle</p>

	</footer>

	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

</body>
</html>