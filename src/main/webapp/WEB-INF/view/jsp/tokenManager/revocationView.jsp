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
	<section class="container">
		
		<c:if test="${not empty ticketDestroyed}">
			<p class="alert alert-success alert-dismissable">
				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
				Ticket has been successfully destroyed.
			</p>
		</c:if>

		<c:if test="${not empty userTickets}">
		<table style="margin: 20px auto" class="table table-responsive"> 
			<thead>
				<tr class="active">
					<th>Username</th>
					<th>Creation time</th>
					<th>Expiration Date</th>
					<th>Last time used</th>
					<th>Browser / Device</th>
					<th>Spot</th>
					<th>Actions</th>
				</tr>				
			</thead>
			<tbody>
				<c:forEach items="${userTickets}" var="auth" varStatus="status">
				<tr>
					<c:set var="authentication" value="${auth.key}"/>
					<c:set var="ticket" value="${auth.value}"/>

					<c:if test="${status.index lt 1}">
						<td rowspan="${fn:length(userTickets)}">
							${authenticatedUser}
						</td>
					</c:if>

					<td>
						<jsp:useBean id="creationTime" class="java.util.Date" />
						<jsp:setProperty name="creationTime" property="time" value="${ticket.creationTime}" />
						<fmt:formatDate value="${creationTime}" type="both" />
					</td>
					<td>

						<c:choose>
							<c:when test="${authentication.attributes['org.jasig.cas.authentication.principal.REMEMBER_ME'] == true}">
								
								<fmt:parseNumber var="expirationPolicy" 
												 integerOnly="true" 
												 type="number" 
												 value="${rememberMeExpirationPolicyInSeconds}" />
							</c:when>
							<c:when test="${authentication.attributes['org.jasig.cas.authentication.principal.REMEMBER_ME'] == false}">
								
								<fmt:parseNumber var="expirationPolicy" 
												 integerOnly="true" 
												 type="number" 
												 value="${expirationPolicyInSeconds}" />
							</c:when>
							<c:otherwise>
								
								<fmt:parseNumber var="expirationPolicy" 
												 integerOnly="true" 
												 type="number" 
												 value="${expirationPolicyInSeconds}" />
							</c:otherwise>
						</c:choose>

						<cas:expirationDate creationDate="${creationTime}" expirationPolicy="${expirationPolicy}" var="expirationDate"/>
						<fmt:formatDate value="${expirationDate}" type="date"/>
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
						<a href="./tokenManager?ticketId=${ticket.id}" class="btn btn-danger">
                        	Revoke
                        </a>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</c:if>

	</section>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

</body>
</html>