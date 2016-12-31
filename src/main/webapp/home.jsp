<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/basic.css">
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
    <section><!--Section Movie-->
        <header class="sm-header fade-in middle">
            <div class="sm-hc"><!--Header Context-->
                <h1 class="sm-hc-title">Movies</h1>
                <ul class="sm-hc-action">
                    <li class="sm-hc-button"><a href="create-movie.jsp">Add Movie</a></li>
                    <li class="sm-hc-button"><a href="movies.html">View All</a></li>
                </ul>
            </div>
        </header>
        <div class="sm-movies fade-in first">
            <jsp:useBean id="movies" scope="request" type="java.util.List"/>
            <ul class="sm-context"> <!--Movies-->
                <c:forEach items="${movies}" var="movie">
                    <li class="sm-cm">
                        <a class="sm-cm-link" href="movie?movie=${movie}&command=show_movie">
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
                <h1 class="sa-hc-title">Actors</h1>
                <ul class="sa-hc-action">
                    <li class="sa-hc-button"><a href="create-actor.jsp">Add Actor</a></li>
                    <li class="sa-hc-button"><a href="movies.html">View All</a></li>
                </ul>
            </div>
        </header>
        <div class="sa-actors fade-in first">
            <jsp:useBean id="actors" scope="request" type="java.util.List"/>
            <ul class="sa-context"> <!--Actors-->
                <c:forEach items="${actors}" var="actor">
                    <li class="sa-ca">
                        <a class="sa-ca-link" href="movie.html">
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

