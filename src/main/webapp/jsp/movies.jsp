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
  <link rel="stylesheet" type="text/css" href="../css/basics.css">
  <script type="text/javascript" src="../js/function.js"></script>
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
  <section><!--Section Movie-->
    <header class="sm-header fade-in middle">
      <div class="sm-hc"><!--Header Context-->
        <h1 class="sm-hc-title"><fmt:message key="movies.section.movies.header"/></h1>
        <ul class="sm-hc-action">
          <c:if test="${user.admin}">
            <li class="sm-hc-button"><a href="movie?command=create_movie"><fmt:message key="movies.section.movies.action.add"/></a></li>
          </c:if>
        </ul>
      </div>
    </header>
    <div class="sm-movies fade-in first">
      <jsp:useBean id="movies" scope="request" type="java.util.List"/>
      <ul class="sm-context"> <!--Movies-->
        <c:forEach items="${movies}" var="movie">
          <li class="sm-cm">
            <a class="sm-cm-link" href="movie?movie_id=${movie.movieId}&command=show_movie">
              <img src="${movie.poster}" alt="Movie">
              <span class="sm-movie-title">${movie.title}</span>
            </a>
          </li>
        </c:forEach>
      </ul>
    </div>
    <div class=pagination-context>
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