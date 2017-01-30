<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale.language}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
  <title></title>
  <link rel="stylesheet" type="text/css" href="../css/basics.css">
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
  <section class="sc fade-in middle"><!--Section Create-->
    <img src="../images/recovery.jpg" alt="Recovery">
    <h1 class="sc-title"><scan class="sc-title-text fade-in latest"><fmt:message key="personal.page.title"/></scan></h1>
  </section>
  <section class="spi"><!--Section Personal Information-->
      <div class="spi-context spi-context-font">
        <ul class="si-list">
          <li class="si-item">
            <fmt:message key="personal.page.email"/> : ${user.email}
          </li>
          <li class="si-item">
            <fmt:message key="personal.page.first.name"/> : ${user.firstName}
          </li>
          <li class="si-item">
            <fmt:message key="personal.page.last.name"/> : ${user.lastName}
          </li>
          <li class="si-item">
            <a class="si-form-button" href="movie?command=logout"><fmt:message key="personal.page.action.exit"/></a>
          </li>
        </ul>
      </div>
  </section>
</main>
<jsp:include page="global-footer.jsp"/>
</body>
</html>
