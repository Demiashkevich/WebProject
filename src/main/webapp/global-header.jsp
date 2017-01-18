<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<header>
  <nav class="gn fade-in middle"><!--Global Navigation-->
    <div class="gn-context">
      <ul class="gn-list">
        <li class="gn-item"><a href="movie"><span class="gn-link-text"><fmt:message key="gn.home"/></span></a></li>
        <li class="gn-item"><a href="movie?command=show_movies&currentPage=1"><span class="gn-link-text"><fmt:message key="gn.movies"/></span></a></li>
        <li class="gn-item"><a href="movie?command=show_actors&currentPage=1"><span class="gn-link-text"><fmt:message key="gn.actors"/></span></a></li>
        <li class="gn-item gn-item-search"><a href="#"><img src="images/search.svg" alt="search"></a></li>
        <li class="gn-item gn-item-sign">
          <c:choose>
            <c:when test="${user != null}">
              <a href="personal-page.jsp"><span class="gn-link-text">${user.firstName} ${user.lastName}</span></a>
            </c:when>
            <c:otherwise>
              <a href="authorization.jsp?from=${pageContext.request.requestURI}">
                <img src="images/sign.svg" alt="sign"/>
              </a>
            </c:otherwise>
          </c:choose>
        </li>
      </ul>
    </div>
  </nav>
</header>
