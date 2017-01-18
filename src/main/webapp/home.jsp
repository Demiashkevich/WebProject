<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="css/basicsss.css">
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
    <section><!--Section Movie-->
        <header class="sm-header fade-in middle">
            <div class="sm-hc"><!--Header Context-->
                <h1 class="sm-hc-title"><fmt:message key="home.section.movie.header"/></h1>
                <ul class="sm-hc-action">
                    <c:if test="${user.admin == true}">
                        <li class="sm-hc-button"><a href="movie?command=create_movie"><fmt:message key="home.section.movie.action.add"/></a></li>
                    </c:if>
                    <li class="sm-hc-button"><a href="movie?command=show_movies&currentPage=1"><fmt:message key="home.section.movie.action.view"/></a></li>
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
    </section>
    <section><!--Section Actors-->
        <header class="sa-header fade-in middle">
            <div class="sa-hc"><!--Header Context-->
                <h1 class="sa-hc-title"><fmt:message key="home.section.actor.header"/></h1>
                <ul class="sa-hc-action">
                    <c:if test="${user.admin == true}">
                        <li class="sa-hc-button"><a href="movie?command=create_actor"><fmt:message key="home.section.actor.action.add"/></a></li>
                    </c:if>
                    <li class="sa-hc-button"><a href="movie?command=show_actors&currentPage=1"><fmt:message key="home.section.actor.action.view"/></a></li>
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
    </section>
</main>
<jsp:include page="global-footer.jsp"/>
</body>
</html>

