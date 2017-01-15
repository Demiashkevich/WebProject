<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="css/basic.css">
    <script type="text/javascript" src="js/functions.js"></script>
</head>
<body>
  <jsp:include page="global-header.jsp"/>
  <main>
    <section class="sc fade-in middle"><!--Section Create-->
      <img src="images/recovery.jpg" alt="Recovery">
      <h1 class="sc-title"><scan class="sc-title-text fade-in latest"><fmt:message key="create.movie.title"/></scan></h1>
    </section>
    <section  class="si-cm fade-in first"><!--Sign Input Create Movie-->
      <form method="get">
        <ul class="si-list">
          <li class="si-item">
            <input class="form-input input-size" type="text" name="title" placeholder="<fmt:message key="create.movie.input.title"/>" autofocus="true" tabindex="1"/>
          </li>
          <li class="si-item">
            <input class="form-input input-size" type="date" name="date" placeholder="<fmt:message key="create.movie.input.date"/>" tabindex="2">
          </li>
          <li class="si-item">
            <textarea class="form-input input-size textarea-input" name="description" placeholder="<fmt:message key="create.movie.input.description"/>" tabindex="3"></textarea>
          </li>
          <li class="si-item">
            <input class="form-input input-size" type="number" name="length" placeholder="<fmt:message key="create.movie.input.length"/>" tabindex="4"/>
          </li>
          <li class="si-item">
            <select name="categories" class="form-input select-size">
              <jsp:useBean id="categories" scope="request" type="java.util.List"/>
              <c:forEach items="${categories}" var="category">
                <option value="${category.categoryId}">${category.name}</option>
              </c:forEach>
            </select>
          </li>
          <li class="si-item">
            <select name="countries" class="form-input select-size">
              <jsp:useBean id="countries" scope="request" type="java.util.List"/>
              <c:forEach items="${countries}" var="country">
                <option value="${country.countryId}">${country.name}</option>
              </c:forEach>
            </select>
          </li>
          <li class="si-item">
            <ul class="si-list-crew">
              <li class="si-item-crew-a">
                <div class="si-crew-ba" onclick="createItem('si-item-cc', 'si-item-crew-context')"><fmt:message key="create.movie.input.action.add.crew"/></div>
              </li>
              <li id="si-item-crew-context">
                <ul id="si-item-cc">
                  <li class="si-item-crew">
                    <select name="crews" class="form-input-crew">
                      <jsp:useBean id="crews" scope="request" type="java.util.List"/>
                      <c:forEach items="${crews}" var="crew">
                        <option value="${crew.crewId}">${crew.firstName} ${crew.lastName}</option>
                      </c:forEach>
                    </select>
                  </li>
                  <li class="si-item-crew">
                    <select name="roles" class="form-input-role">
                      <jsp:useBean id="roles" scope="request" type="java.util.List"/>
                      <c:forEach items="${roles}" var="role">
                        <option value="${role.roleId}">${role.name}</option>
                      </c:forEach>
                    </select>
                  </li>
                  <li class="si-item-crew si-item-crew-br">
                    <div class="si-crew-button" onclick="removeItem(this)"><fmt:message key="create.movie.input.action.remove"/></div>
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
                <ul id="si-item-ac">
                  <li class="si-item-actor">
                    <select name="actors" class="form-input-actor">
                      <jsp:useBean id="actors" scope="request" type="java.util.List"/>
                      <c:forEach items="${actors}" var="actor">
                        <option value="${actor.actorId}">${actor.firstName} ${actor.lastName}</option>
                      </c:forEach>
                    </select>
                  </li>
                  <li class="si-item-actor si-item-actor-br">
                    <div class="si-actor-button" onclick="removeItem(this)"><fmt:message key="create.movie.input.action.remove"/></div>
                  </li>
                </ul>
              </li>
            </ul>
          </li>
          <li class="si-item">
            <button class="si-form-button" type="submit"><fmt:message key="create.movie.title"/></button>
            <input type="hidden" value="add_movie" name="command">
          </li>
        </ul>
      </form>
    </section>
  </main>
  <jsp:include page="global-footer.jsp"/>
</body>
</html>
