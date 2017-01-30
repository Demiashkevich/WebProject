<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale.language}"/>
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
      <h1 class="sc-title"><scan class="sc-title-text fade-in latest"><fmt:message key="create.movie.title"/></scan></h1>
    </section>
    <section  class="si-cm fade-in first"><!--Sign Input Create Movie-->
      <form method="post" action="movie">
        <ul class="si-list">
          <li class="si-item">
            <input class="form-input input-size" type="text" name="title" placeholder="<fmt:message key="create.movie.input.title"/>" autofocus="true" tabindex="1" required/>
          </li>
          <li class="si-item">
            <input class="form-input input-size" type="date" name="date" placeholder="<fmt:message key="create.movie.input.date"/>" tabindex="2" required/>
          </li>
          <li class="si-item">
            <textarea class="form-input input-size textarea-input" name="description" placeholder="<fmt:message key="create.movie.input.description"/>" tabindex="3" required></textarea>
          </li>
          <li class="si-item">
            <input class="form-input input-size" type="number" min="1" max="10" name="length" placeholder="<fmt:message key="create.movie.input.length"/>" tabindex="4" required/>
          </li>
          <li class="si-item">
            <ul class="si-list-category">
              <li class="si-item-category-a">
                <div class="si-category-ba" onclick="createItem('si-item-categories', 'si-item-category-context')"><fmt:message key="create.movie.input.action.add.category"/></div>
              </li>
              <li id="si-item-category-context">
                <ul id="si-item-categories" class="category-marker">
                  <li class="si-item-country">
                    <select name="categories" class="form-input fi-movie-size">
                      <c:forEach items="${categories}" var="category">
                        <option value="${category.categoryId}">${category.name}</option>
                      </c:forEach>
                    </select>
                  </li>
                  <li class="si-item-category si-item-category-br">
                    <div class="si-category-button" onclick="removeItem(this, 'category-marker')"><fmt:message key="create.movie.input.action.remove"/></div>
                  </li>
                </ul>
              </li>
            </ul>
          </li>
          <li class="si-item">
            <ul class="si-list-country">
              <li class="si-item-country-a">
                <div class="si-actor-ba" onclick="createItem('si-item-countries', 'si-item-country-context')"><fmt:message key="create.movie.input.action.add.country"/></div>
              </li>
              <li id="si-item-country-context">
                <ul id="si-item-countries" class="country-marker">
                  <li class="si-item-country">
                    <select name="countries" class="form-input fi-movie-size">
                      <c:forEach items="${countries}" var="country">
                        <option value="${country.countryId}">${country.name}</option>
                      </c:forEach>
                    </select>
                  </li>
                  <li class="si-item-country si-item-country-br">
                    <div class="si-country-button" onclick="removeItem(this, 'country-marker')"><fmt:message key="create.movie.input.action.remove"/></div>
                  </li>
                </ul>
              </li>
            </ul>
          </li>
          <li class="si-item">
            <ul class="si-list-crew">
              <li class="si-item-crew-a">
                <div class="si-crew-ba" onclick="createItem('si-item-cc', 'si-item-crew-context')"><fmt:message key="create.movie.input.action.add.crew"/></div>
              </li>
              <li id="si-item-crew-context">
                <ul id="si-item-cc" class="crew-marker">
                  <li class="si-item-crew">
                    <select name="crews" class="form-input-crew">
                      <c:forEach items="${crews}" var="crew">
                        <option value="${crew.crewId}">${crew.firstName} ${crew.lastName}</option>
                      </c:forEach>
                    </select>
                  </li>
                  <li class="si-item-crew">
                    <select name="roles" class="form-input-role">
                      <c:forEach items="${roles}" var="role">
                        <option value="${role.roleId}">${role.name}</option>
                      </c:forEach>
                    </select>
                  </li>
                  <li class="si-item-crew si-item-crew-br">
                    <div class="si-crew-button" onclick="removeItem(this, 'crew-marker')"><fmt:message key="create.movie.input.action.remove"/></div>
                  </li>
                </ul>
              </li>
            </ul>
          </li>
          <li class="si-item">
            <ul class="si-list-actor">
              <li class="si-item-actor-a">
                <div class="si-actor-ba" onclick="createItem('si-item-ac', 'si-item-actor-context')"><fmt:message key="create.movie.input.action.add.actor"/></div>
              </li>
              <li id="si-item-actor-context">
                <ul id="si-item-ac" class="actor-marker">
                  <li class="si-item-actor">
                    <select name="actors" class="form-input fi-movie-size">
                      <c:forEach items="${actors}" var="actor">
                        <option value="${actor.actorId}">${actor.firstName} ${actor.lastName}</option>
                      </c:forEach>
                    </select>
                  </li>
                  <li class="si-item-actor si-item-actor-br">
                    <div class="si-actor-button" onclick="removeItem(this, 'actor-marker')"><fmt:message key="create.movie.input.action.remove"/></div>
                  </li>
                </ul>
              </li>
            </ul>
          </li>
          <li class="si-item">
            <button class="si-form-button" type="submit"><fmt:message key="create.movie.input.action.add"/></button>
            <input type="hidden" value="add_movie" name="command">
          </li>
        </ul>
      </form>
    </section>
  </main>
  <jsp:include page="../global-footer.jsp"/>
</body>
</html>
