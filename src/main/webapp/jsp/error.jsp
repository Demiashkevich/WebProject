<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" uri="http://demiashkevich.com/jsp/tlds/pagination"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
  <title></title>
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="../css/basics.css">
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
  <section class="sp"><!--Section Picture-->
    <img src="../images/recovery.jpg" alt="Recovery">
  </section>
  <section><!--Section Error-->
    <div class="ss-context">
      <div class="ss-item">
        <img class="ss-sl" src="../images/error.png" alt="Success">
        <span class="ss-record"><fmt:message key="error.title"/></span>
      </div>
      <div class="si-item">
        ${error}
      </div>
      <div class="ss-action">
        <custom:button-snapshot><fmt:message key="error.action"/></custom:button-snapshot>
      </div>
    </div>
  </section>
</main>
<jsp:include page="global-footer.jsp"/>
</body>
</html>
