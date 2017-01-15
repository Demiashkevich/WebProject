<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="actor" scope="request" type="com.demiashkevich.movie.entity.Actor"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
    <title>Movie</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/basic.css">
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
    <section><!--Section Actor-->
        <header class="sa-header fade-in middle">
            <div class="sa-hc"><!--Header Context-->
                <h1 class="sa-hc-title">${actor.firstName} ${actor.lastName}</h1>
                <div class="sa-hc-action">
                    <div class="sa-hc-button"><a href=""><fmt:message key="actor.action.edit"/></a></div>
                    <div class="sa-hc-button"><a href="movie?command=delete_actor&actor_id=${actor.actorId}"><fmt:message key="actor.action.delete"/></a></div>
                </div>
            </div>
        </header>
        <div class="am fade-in first"><!--Article Actor-->
            <div class="am-context">
                <article><!--Actor Information-->
                    <div class="am-poster">
                        <img src="${actor.photo}" alt="Actor">
                    </div>
                    <div class="aa"><!--Description-->
                            <b><fmt:message key="actor.biography"/></b>: ${actor.biography}
                    </div>
                </article>
                <article>
                    <header class="aa-header fade-in middle">
                        <div class="aa-hc"><!--Header Context-->
                            <h1 class="aa-hc-title"><fmt:message key="actor.movies"/></h1>
                        </div>
                    </header>
                    <div class="aa-actors fade-in first"><!--Actor Movie-->
                        <ul class="aa-context">
                            <c:forEach items="${actor.movies}" var="movie">
                                <li class="aa-ca">
                                    <a class="aa-ca-link" href="movie?command=show_movie&movie_id=${movie.movieId}">
                                        <img src="${movie.poster}" alt="Movie">
                                        <span class="aa-actor-title">${movie.title}</span>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </article>
            </div>
        </div>
    </section>
</main>
<jsp:include page="global-footer.jsp"/>
</body>
</html>
