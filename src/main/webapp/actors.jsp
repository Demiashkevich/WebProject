<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" uri="http://demiashkevich.com/jsp/tlds/pagination"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
  <title></title>
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="css/basic.css">
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
  <section><!--Section Actors-->
    <header class="sa-header fade-in middle">
      <div class="sa-hc"><!--Header Context-->
        <h1 class="sa-hc-title"><fmt:message key="actors.section.actors.header"/></h1>
        <ul class="sa-hc-action">
          <c:if test="${user.admin == true}">
            <li class="sa-hc-button"><a href="movie?command=create_actor"><fmt:message key="actors.section.actors.action.add"/></a></li>
          </c:if>
        </ul>
      </div>
    </header>
    <div class="sa-actors fade-in first">
      <jsp:useBean id="actors" scope="request" type="java.util.List"/>
      <ul class="sa-context"> <!--Actors-->
        <c:forEach items="${actors}" var="actor">
          <li class="sa-ca">
            <a class="sa-ca-link" href="movie?command=show_actor&actor_id=${actor.actorId}">
              <img src="${actor.photo}" alt="Actor">
              <span class="sm-movie-title">${actor.firstName} ${actor.lastName}</span>
            </a>
          </li>
        </c:forEach>
      </ul>
    </div>
    <div class="pagination-context">
      <custom:pag-previous currentPage="${currentPage}">
        <fmt:message key="movies.section.pagination.previous"/>
      </custom:pag-previous>
      <custom:pag-body currentPage="${currentPage}" countPage="${countPage}"/>
      <custom:pag-next  currentPage="${currentPage}" countPage="${countPage}">
        <fmt:message key="movies.section.pagination.next"/>
      </custom:pag-next>
    </div>
  </section>
</main>
<jsp:include page="global-footer.jsp"/>
</body>
</html>

