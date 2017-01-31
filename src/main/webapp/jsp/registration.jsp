<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="../css/basics.css">
    <script type="text/javascript" src="../js/function.js"></script>
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
  <section class="sc fade-in middle"><!--Section Create-->
    <img src="../images/recovery.jpg" alt="Recovery">
    <h1 class="sc-title"><scan class="sc-title-text fade-in latest"><fmt:message key="registration.title"/></scan></h1>
  </section>
  <section  class="si fade-in first"><!--Sign Input-->
    <form action="movie" name="registration_form" method="post" onsubmit="return validate()">
      <ul class="si-list">
        <li class="si-item">
          <input class="form-input  size-input-sign-up" value="${email}" type="email" name="email" placeholder="<fmt:message key="registration.email"/>" autofocus="false" tabindex="1"/>
        </li>
        <li class="si-item">
          <input class="form-input size-input-sign-up" value="${password}" type="password" name="password" placeholder="<fmt:message key="registration.password"/>" tabindex="2">
        </li>
        <li class="si-item">
          <input class="form-input size-input-sign-up" value="${password}" type="password" name="password_confirm" placeholder="<fmt:message key="registration.confirm.password"/>" tabindex="3">
        </li>
        <li class="si-item">
          <input class="form-input size-input-sign-up" value="${first_name}" type="text" name="first_name" placeholder="<fmt:message key="registration.first.name"/>" tabindex="4"/>
        </li>
        <li class="si-item">
          <input class="form-input size-input-sign-up" value="${last_name}" type="text" name="last_name" placeholder="<fmt:message key="registration.last.name"/>" tabindex="5"/>
        </li>
        <li class="si-item">
          <button class="si-form-button" type="submit"><fmt:message key="registration.action.create"/></button>
          <input type="hidden" value="sign_up" name="command">
          <scan>${message_error}</scan>
        </li>
      </ul>
    </form>
  </section>
</main>
<jsp:include page="global-footer.jsp"/>
</body>
</html>
