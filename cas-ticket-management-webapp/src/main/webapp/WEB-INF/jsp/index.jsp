<jsp:directive.include file="includes/header.jsp" />

	<h1>CAS addon ticket management webapp</h1>
	
	<ul>
		<li>
			<c:url value="/user" var="userIndex"/>
			<a href="${userIndex}">
				Interface de révocation utilisateur
			</a>
		</li>
		<li>
			<c:url value="/admin" var="adminIndex"/>
			<a href="${adminIndex}">
				Interface de révocation administrateur
			</a>
		</li>
	</ul>
	
<jsp:directive.include file="includes/footer.jsp" />
