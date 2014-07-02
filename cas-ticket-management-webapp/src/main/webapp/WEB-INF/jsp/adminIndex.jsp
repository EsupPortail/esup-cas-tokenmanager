<jsp:directive.include file="includes/header.jsp" />

	<h1><spring:message code="header.title"/> <small><spring:message code="header.title.admin"/></small></h1>
	
	<c:if test="${delete}">
		<div class="modal fade" id="modal-delete"><div class="modal-dialog"><div class="modal-content">
		      
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">
					<spring:message code="admin.modal.title"/>
				</h4>
			</div>
			<div class="modal-body">
				<p>
					<spring:message code="admin.modal.body"/>
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-success" data-dismiss="modal">
					<spring:message code="admin.modal.close"/>
				</button>
			</div>

			<script type="text/javascript">
				window.onload=function(){ $('#modal-delete').modal(); };
			</script>

		</div></div></div>
	</c:if>
	
	<form:form method="post" action="admin/deleteAll" role="form">
		
		<fieldset>
			<legend>
				<spring:message code="admin.form.legend"/>
			</legend>
	
			<p class="alert alert-warning alert-dismissable">
				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
				<spring:message code="admin.form.alert"/>
			</p>
			
			<div class="form-group">
				<form:label path="owner">
					<spring:message code="admin.form.label"/>
				</form:label>
			
				<spring:message code="admin.form.placeholder" var="adminFormPlaceholder"/>
				<form:input path="owner" placeholder="${adminFormPlaceHolder}" class="form-control" required/>
			</div>
			<button type="submit" class="btn btn-danger">
				<spring:message code="admin.form.submit"/>
			</button>
		</fieldset>
				
	</form:form>

<jsp:directive.include file="includes/footer.jsp" />
