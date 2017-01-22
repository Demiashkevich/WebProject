<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="css/basic.css">
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
  <section class="sc"><!--Section Create-->
    <img src="images/recovery.jpg" alt="Recovery">
    <h1 class="sc-title"><scan class="sc-title-text fade-in latest"><fmt:message key="create.review.title"/> ${param.movie_title}</scan></h1>
  </section>
  <section  class="dr"><!--Description Review-->
    <form method="post" action="movie">
      <ul class="dr-list">
        <li class="dr-item">
          <input class="form-input" value="${evaluation.title}" type="text" name="title" placeholder="<fmt:message key="create.review.title.review"/>" autofocus="true" tabindex="1" required="true"/>
        </li>
        <li class="dr-item">
          <textarea class="form-input textarea-input" name="comment" maxlength="2048" tabindex="2">${evaluation.comment}</textarea>
        </li>
        <li class="dr-item">
          <input class="form-input" value="${evaluation.rating}" type="number" name="rating" placeholder="<fmt:message key="create.review.rating"/>" tabindex="3" required="true"/>
        </li>
        <li class="dr-item">
          <button class="dr-form-button" type="submit"><fmt:message key="create.review.add.review"/></button>
          <input type="hidden" value="${param.command_action}" name="command">
          <input type="hidden" value="${param.movie_id}" name="movie_id">
          <input type="hidden" value="${param.user_id}" name="user_id">
        </li>
      </ul>
    </form>
  </section>
</main>
<jsp:include page="global-footer.jsp"/>
</body>
</html>
