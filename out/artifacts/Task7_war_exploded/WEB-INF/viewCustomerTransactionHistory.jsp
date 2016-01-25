<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />

<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<h2 class="page-header">${customer.getFirstName()}
			${customer.getLastName()}</h2>
	</div>
</div>
<div class="row">
		<c:forEach var="error" items="${errors}">
			<h3 style="color: red">${error}</h3>
		</c:forEach>
</div>
<div class="row">
	<div class="col-md-offset-2 col-md-8">
		<h2 align="center">Transaction History</h2>
		<div class="table-responsive">
			<table class="table table-bordered table-hover table-striped">
				<tr>
					<td>Date</td>
					<td>Operation</td>
					<td>Fund Name</td>
					<td>Shares</td>
					<td>Share Price</td>
					<td>Amount</td>
				</tr>
				<c:forEach var="h" items="${historyRecord}">
					<tr>
						<td>${ h.getExecuteDate() }</td>
						<td>${ h.getTransactionType() }</td>
						<td>${ h.getFundName() }</td>
						<td>${ h.getShares() }</td>
						<td>${ h.getSharePrice() }</td>
						<td>${ h.getAmount() }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>

<jsp:include page="template-buttom.jsp" />