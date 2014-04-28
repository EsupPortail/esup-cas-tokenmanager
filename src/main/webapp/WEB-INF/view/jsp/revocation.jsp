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
		
		<c:if test="${not empty destroyTicketMessage}">

			<p class="alert alert-success alert-dismissable">
			  <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			  ${destroyTicketMessage}
			</p>
		</c:if>

		<c:if test="${not empty userTickets}">
		<table style="margin: 20px auto" class="table table-responsive"> 
			<tbody>
				<c:forEach items="${userTickets}" var="userTicket">
					<tr class="active">
						<th>Username</th>
						<th>Creation time</th>
						<th>Last time used</th>
						<th>User Agent</th>
						<th>IP Address</th>
						<th>Actions</th>
					</tr>
					<c:forEach items="${userTicket.value}" var="authTicket" varStatus="status">
					<tr>

						<c:set var="authentication" value="${authTicket.key}"/>
						<c:set var="ticket" value="${authTicket.value}"/>

						<c:if test="${status.index lt 1}">
							<td rowspan="${fn:length(userTicket.value)}">
								${userTicket.key}
							</td>
						</c:if>

						<td>
							<jsp:useBean id="creationTime" class="java.util.Date" />
							<jsp:setProperty name="creationTime" property="time" value="${ticket.creationTime}" />
							<fmt:formatDate value="${creationTime}" type="both" />
						</td>
						<td>                
	                        <cas:timeConverter time="${ticket.lastTimeUsed}"/>                    					
						</td>
						<td>
							<cas:uaDetector userAgent="${authentication.attributes.userAgent}"/>
						</td>
						<td>
							<cas:ipLocator ipAddress="${authentication.attributes.ipAddress}"/>
						</td>
						<td>
							<a href="./revocation.html?ticket=${ticket.id}" class="btn btn-danger">
                            	Révoquer
	                        </a>
						</td>

					</tr>
					</c:forEach>
				</c:forEach>		
			</tbody>
		</table>
		</c:if>


	</section>
	
	<footer class="container">
		
		<hr>

		<p>Cas webapp admin - Université de La Rochelle</p>

	</footer>

	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

</body>
</html>