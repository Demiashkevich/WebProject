<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="movie" scope="session" type="com.demiashkevich.movie.entity.Movie"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
  <title></title>
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="../css/basic.css">
</head>
<body>
  <jsp:include page="global-header.jsp"/>
  <main>
    <section><!--Section Movie-->
      <header class="sm-header fade-in middle">
        <div class="sm-hc"><!--Header Context-->
          <h1 class="sm-hc-title">${movie.title}</h1>
          <c:if test="${user.admin}">
            <div class="sm-hc-action">
              <div class="sm-hc-button"><a href="movie?command=edit_movie"><fmt:message key="movie.action.edit"/></a></div>
              <div class="sm-hc-button"><a href="movie?command=delete_movie&movie_id=${movie.movieId}"><fmt:message key="movie.action.delete"/></a></div>
            </div>
          </c:if>
        </div>
      </header>
      <div class="am fade-in first"><!--Article Movie-->
        <div class="am-context">
          <article><!--Movie Description-->
            <div class="am-poster">
              <img src="${movie.poster}" alt="Movie">
            </div>
            <ul class="am-list"><!--Description Enum-->
              <li class="am-item">
                <b><fmt:message key="movie.description"/></b>: ${movie.description}
              </li>
              <li class="am-item"><b><fmt:message key="movie.date"/></b>: ${movie.date}</li>
              <li class="am-item"><b><fmt:message key="movie.length"/></b>: ${movie.length} min</li>
              <li class="am-item"><b><fmt:message key="movie.country"/></b>: <c:forEach items="${movie.countries}" var="country"> ${country.name}</c:forEach> </li>
              <li class="am-item"><b><fmt:message key="movie.category"/></b>: <c:forEach items="${movie.categories}" var="category"> ${category.name}</c:forEach> </li>
              <li class="am-item"><b><fmt:message key="movie.crew"/></b>:
                <ul class="am-item-crew">
                  <c:forEach items="${movie.crews}" var="crew">
                    <li>${crew.role.name}: ${crew.firstName} ${crew.lastName}</li>
                  </c:forEach>
                </ul>
              </li>
              <li><b><fmt:message key="movie.rating"/></b>: ${movie.rating}</li>
            </ul>
          </article>
          <article>
            <header class="aa-header fade-in middle">
              <div class="aa-hc"><!--Header Context-->
                <h1 class="aa-hc-title"><fmt:message key="movie.actors"/></h1>
              </div>
            </header>
            <div class="aa-actors fade-in first"><!--Movie Actors-->
              <ul class="aa-context">
                <c:forEach items="${movie.actors}" var="actor">
                  <li class="aa-ca">
                    <a class="aa-ca-link" href="movie?command=show_actor&actor_id=${actor.actorId}">
                      <img src="${actor.photo}" alt="Actor">
                      <span class="aa-actor-title">${actor.firstName} ${actor.lastName}</span>
                    </a>
                  </li>
                </c:forEach>
              </ul>
            </div>
          </article>
        </div>
      </div>
    </section>
    <section><!--Section Comments-->
      <header class="sc-header">
        <div class="sc-hc"><!--Header Context-->
          <h1 class="sc-hc-title"><fmt:message key="movie.section.review.header"/></h1>
          <div class="sc-hc-action">
            <div class="sc-hc-button"><a href="movie?command=create_review&command_action=add_review"><fmt:message key="movie.action.add.review"/></a></div>
          </div>
        </div>
      </header>
      <c:forEach items="${movie.evaluations}" var="evaluation">
        <article><!--Review-->
          <header class="r-header">
            <div class="r-hc"><!--Header Context-->
              <h1 class="r-hc-title">${evaluation.title}</h1>
              <ul class="sr-hc-action">
                <li class="sr-hc-button"><div class="r-mark">${evaluation.rating}</div></li>
                <c:if test="${user.userId == evaluation.user.userId || user.admin}">
                  <li class="sr-hc-button">
                    <form method="post" action="movie">
                      <input type="hidden" value="edit_review" name="command"/>
                      <input type="hidden" value="update_review" name="command_action"/>
                      <input type="hidden" value="${evaluation.title}" name="title"/>
                      <input type="hidden" value="${evaluation.comment}" name="comment"/>
                      <input type="hidden" value="${evaluation.rating}" name="rating"/>
                      <input type="hidden" value="${movie.movieId}" name="movie_id"/>
                      <input type="hidden" value="${movie.title}" name="movie_title"/>
                      <input type="hidden" value="${evaluation.user.userId}" name="user_id"/>
                    <a href="movie?command=edit_review&command_action=update_review&title=${evaluation.title}&comment=${evaluation.comment}&rating=${evaluation.rating}&movie_id=${movie.movieId}&movie_title=${movie.title}&user_id=${evaluation.user.userId}">
                      <fmt:message key="movie.action.edit"/>
                    </a>
                      <button type="submit"><fmt:message key="movie.action.edit"/></button>
                    </form>
                  </li>
                  <li class="sr-hc-button"><a href="movie?command=delete_review&movie_id=${movie.movieId}&user_id=${evaluation.user.userId}"><fmt:message key="movie.action.delete"/></a></li>
                </c:if>
              </ul>
            </div>
          </header>
          <p class="r-context">
            ${evaluation.comment}
          </p>
          <footer class="r-footer">
            <div class="r-writer">
              ${evaluation.user.firstName} ${evaluation.user.lastName}
            </div>
          </footer>
        </article>
      </c:forEach>
    </section>
  </main>
  <jsp:include page="global-footer.jsp"/>
</body>
</html>
