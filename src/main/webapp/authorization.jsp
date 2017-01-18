<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/basicsss.css">
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
  <section class="sc fade-in middle"><!--Section Create-->
    <img src="images/recovery.jpg" alt="Recovery">
    <h1 class="sc-title"><scan class="sc-title-text fade-in latest"><fmt:message key="authorization.title"/></scan></h1>
  </section>
  <section  class="si fade-in first"><!--Section Input-->
    <form method="get" action="movie">
      <ul class="si-list">
        <li class="si-item">
          ${message_error}
        </li>
        <li class="si-item">
          <input class="form-input" type="text" name="email" required="true" placeholder="<fmt:message key="authorization.input.email"/>" autofocus="true" tabindex="1" autocomplete="false"/>
        </li>
        <li class="si-item">
          <input class="form-input" type="password" name="password" required="true" placeholder="<fmt:message key="authorization.input.password"/>" tabindex="2" autocomplete="false"/>
        </li>
        <li class="si-item">
          <a class="si-fc-link si-link-sign" href="registration.jsp"><fmt:message key="authorization.sign-up"/></a>
          <button class="si-fc-button si-form-button" type="submit"><fmt:message key="authorization.sign-in"/></button>
          <input type="hidden" name="command" value="sign_in">
        </li>
      </ul>
    </form>
  </section>
</main>
<jsp:include page="global-footer.jsp"/>
</body>
</html>
