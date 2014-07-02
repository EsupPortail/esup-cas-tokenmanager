<jsp:directive.include file="includes/header.jsp" />

	<h1>
		<spring:message code="header.title"/>
	</h1>
	
	<ul>
		<li>
			<c:url value="/user" var="userIndex"/>
			<a href="${userIndex}">
				<spring:message code="index.user.link"/>
			</a>
		</li>
		<li>
			<c:url value="/admin" var="adminIndex"/>
			<a href="${adminIndex}">
				<spring:message code="index.admin.link"/>
			</a>
		</li>
	</ul>
	
<jsp:directive.include file="includes/footer.jsp" />
