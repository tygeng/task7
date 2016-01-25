<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-employee-top.jsp" />
<div>
	<h3>Welcome, ${ user.firstName } ${ user.lastName }!</h3>
</div>
<jsp:include page="template-buttom.jsp" />