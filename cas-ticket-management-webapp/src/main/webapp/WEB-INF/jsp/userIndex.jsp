<jsp:directive.include file="includes/header.jsp" />

	<c:if test="${delete}">
		<div class="modal fade" id="modal-delete"><div class="modal-dialog"><div class="modal-content">
		      
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">
					<spring:message code="user.modal.title"/>
				</h4>
			</div>
			<div class="modal-body">
				<p>
					<spring:message code="user.modal.body"/>
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-success" data-dismiss="modal">
					<spring:message code="user.modal.close"/>
				</button>
			</div>

			<script type="text/javascript">
				window.onload=function(){ $('#modal-delete').modal(); };
			</script>

		</div></div></div>
	</c:if>

	<h1>
		<spring:message code="header.title"/>
	</h1>

	<c:choose>
		<c:when test="${fn:length(userTickets) eq 0}">
		
			<p class="alert alert-warning alert-dismissable">
				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
				<spring:message code="user.alert.empty"/>
			</p>
		
		</c:when>
		<c:otherwise>
		
			<table class="table table-responsive table-striped table-hover">

				<caption>
					<spring:message code="user.table.caption"/> <strong>${userTickets[0].owner}</strong>
				</caption>
		
				<thead>
					<tr>
						<th>
							<spring:message code="user.table.device"/>
						</th>
						<th>
							<spring:message code="user.table.creationDate"/>
						</th>
						<th>
							<spring:message code="user.table.expirationDate"/>
						</th>
						<th>
							<spring:message code="user.table.spot"/>
						</th>
						<th>
							<spring:message code="user.table.action"/>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="ticket" items="${userTickets}" varStatus="status">
					<tr>
						<td>
							<cas:uaDetector userAgent="${ticket.authenticationAttributes.userAgent}"/>
						</td>
						<td>
							<jsp:useBean id="creationTime" class="java.util.Date" />
							<jsp:setProperty name="creationTime" property="time" value="${ticket.creationTime}" />
							<fmt:formatDate value="${creationTime}" type="both" />
						</td>
						<td>							
							<c:choose>
								<c:when test="${ticket.authenticationAttributes['org.jasig.cas.authentication.principal.REMEMBER_ME'] == true}">
									
									<fmt:parseNumber var="expirationPolicy" 
													 integerOnly="true" 
													 type="number" 
													 value="${rememberMeExpirationPolicyInSeconds}" />
								</c:when>
								<c:when test="${ticket.authenticationAttributes['org.jasig.cas.authentication.principal.REMEMBER_ME'] == false}">
									
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
							<c:if test="${activateIpGeolocation}">
								<cas:ipLocator ipAddress="${ticket.authenticationAttributes.ipAddress}"/>
							</c:if>
							( <cas:timeConverter time="${ticket.lastTimeUsed}"/> )
						</td>
						<td>
							<c:url value="/user/delete/${ticket.id}" var="revokeUrl"/>
							<a href="${revokeUrl}" class="btn btn-danger">
								<spring:message code="user.table.revoke"/>
							</a>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		
			<ul class="pagination">
				<c:forEach var="i" begin="1" end="${pageNumber + 1}">
					<c:url value="/user" var="paginatorUrl">
						<c:param name="page" value="${i}"/>
					</c:url>
					<c:choose>
						<c:when test="${i==(currentPage+1)}">
							<c:set var="activeClass" value="class=\"active\""/>
						</c:when>
						<c:otherwise>
							<c:set var="activeClass" value=""/>
						</c:otherwise>
					</c:choose>
					<li ${activeClass}>
						<a href="${paginatorUrl}">${i}</a>
					</li>
				</c:forEach>
			</ul>
		</c:otherwise>
	</c:choose>

<jsp:directive.include file="includes/footer.jsp" />