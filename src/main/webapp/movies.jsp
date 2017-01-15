<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
  <title></title>
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="css/basic.css">
  <script type="text/javascript" src="js/functions.js"></script>
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
  <section><!--Section Movie-->
    <header class="sm-header fade-in middle">
      <div class="sm-hc"><!--Header Context-->
        <h1 class="sm-hc-title"><fmt:message key="movies.section.movies.header"/></h1>
        <ul class="sm-hc-action">
          <c:if test="${user.admin == true}">
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
    <div class="pagination-context">
      <c:if test="${currentPage != 1}">
        <a class="pagination-link" href="movie?command=show_movies&currentPage=${currentPage - 1}"><fmt:message key="movies.section.pagination.previous"/></a>
      </c:if>
      <table class="pagination-table">
        <tr>
          <c:forEach begin="1" end="${countPage}" var="i">
            <c:choose>
              <c:when test="${currentPage == i}">
                <td class="pagination-cp">${i}</td>
              </c:when>
              <c:otherwise>
                <td><a class="pagination-link-page" href="movie?command=show_movies&currentPage=${i}">${i}</a></td>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </tr>
      </table>
      <c:if test="${countPage != 1 && currentPage != countPage}">
        <a class="pagination-link" href="movie?command=show_movies&currentPage=${currentPage + 1}"><fmt:message key="movies.section.pagination.next"/></a>
      </c:if>
    </div>
  </section>
</main>
<jsp:include page="global-footer.jsp"/>
</body>
</html>