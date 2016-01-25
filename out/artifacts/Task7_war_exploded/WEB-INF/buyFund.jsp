<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-customer-top.jsp" />

<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<h2 class="page-header">${customer.getFirstName()}
			${customer.getLastName()}</h2>
	</div>
</div>
<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		</c:forEach>
		<div>
			<form method="POST" action="buyFund.do">
				<table class="table table-hover table-striped">

					<tr>
						<td><b>Available Cash:</b></td>
						<td>${customer.getCash()}</td>
					</tr>


					<tr>
						<td><b>Select the fund symbol:</b></td>
						<td><select class="form-control" name="fund">
								<option></option>

								<c:choose>
									<c:when test="${ (empty fundList) }"></c:when>
									<c:otherwise>
										<c:forEach var="f" items="${ fundList }">
											<option>${ f.getSymbol() }</option>
										</c:forEach>
									</c:otherwise>
								</c:choose>
						</select></td>
					</tr>
					<tr>
						<td><b>Enter the amount:</b></td>
						<td><input type="text" class="form-control" name="amount"
							placeholder="Amount must above $10.00"></td>
					</tr>
					<tr>
						<td></td>
						<td align="right"><button type="submit" name="action"
								value="buy" class="btn btn-default">Buy Fund</button></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>

<jsp:include page="template-buttom.jsp" />