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
			<form method="POST" action="sellFund.do">
				<table class="table table-hover table-striped">
					<tr>
						<td><b>Select the fund you own:</b></td>
					</tr>
					<tr>
						<td>Fund Symbol</td>
						<td>Shares</td>
						<td>Shares to sell</td>
					</tr>
					<tr>
						<td>${ fund.getSymbol() }</td>
						<td>${ fund.getShares() }</td>
						<td><input type="text" class="form-control" name="shares"
							placeholder="Number must below the shares you own"></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td align="right"><button type="submit" name="action"
								value="buy" class="btn btn-default">Sell Fund</button></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>

<jsp:include page="template-buttom.jsp" />