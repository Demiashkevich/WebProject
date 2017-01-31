<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
  <title></title>
  <link rel="stylesheet" type="text/css" href="../../css/basics.css">
  <script type="text/javascript" src="../../js/function.js"></script>
</head>
<body>
<jsp:include page="../global-header.jsp"/>
<main>
  <section class="sc fade-in middle"><!--Section Create-->
    <img src="../../images/recovery.jpg" alt="Recovery">
    <h1 class="sc-title"><scan class="sc-title-text fade-in latest"><fmt:message key="create.actor.title"/></scan></h1>
  </section>
  <section  class="si-ca fade-in first"><!--Sign Input Create Actor-->
    <form method="post" action="movie">
      <ul class="si-list">
        <li class="si-item">
          <input class="form-input input-size" type="text" pattern="[A-zА-яЁё]+" name="first_name" placeholder="<fmt:message key="create.actor.input.first.name"/>" autofocus="true" tabindex="1" required/>
        </li>
        <li class="si-item">
          <input class="form-input input-size" type="text" pattern="[A-zА-яЁё]+" name="last_name" placeholder="<fmt:message key="create.actor.input.last.name"/>" autofocus="true" tabindex="2" required/>
        </li>
        <li class="si-item">
          <textarea class="form-input input-size textarea-input" name="biography" placeholder="<fmt:message key="create.actor.input.biography"/>" tabindex="3" required></textarea>
        </li>
        <li class="si-item">
          <ul class="si-list-movie">
            <li class="si-item-movie-a">
              <div class="si-movie-ba" onclick="createItem('si-item-mc', 'si-item-movie-context')"><fmt:message key="create.actor.input.add.movie"/></div>
            </li>
            <li id="si-item-movie-context">
              <ul id="si-item-mc">
                <li class="si-item-movie">
                  <select name="movies" class="form-input-movie fi-movie-size">
                    <c:forEach items="${movies}" var="movie">
                      <option value="${movie.movieId}">${movie.title}</option>
                    </c:forEach>
                  </select>
                </li>
                <li class="si-item-movie si-item-movie-br">
                  <div class="si-movie-button" onclick="removeItem(this)"><fmt:message key="create.actor.input.remove"/></div>
                </li>
              </ul>
            </li>
          </ul>
        </li>
        <li class="si-item">
          <button class="si-form-button" type="submit"><fmt:message key="create.actor.action.add"/></button>
          <input type="hidden" value="add_actor" name="command">
        </li>
      </ul>
    </form>
  </section>
</main>
<jsp:include page="../global-footer.jsp"/>
</body>
</html>
