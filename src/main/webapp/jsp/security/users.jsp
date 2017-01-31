<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
  <title></title>
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="../../css/basics.css">
</head>
<body>
  <jsp:include page="../global-header.jsp"/>
  <main>
    <div>
      <c:forEach items="${users}" var="user">
        <ul>
          <li>${user.firstName} ${user.lastName}</li>
          <li>
            <a href="movie?command=edit_user_status&user_id=${user.userId}&status=${user.status}">
              <c:choose>
                <c:when test="${user.status}">
                  <fmt:message key="users.action.lock"/>
                </c:when>
                <c:otherwise>
                  <fmt:message key="users.action.unlock"/>
                </c:otherwise>
              </c:choose>
            </a></li>
        </ul>
      </c:forEach>
    </div>
  </main>
  <jsp:include page="../global-footer.jsp"/>
</body>
</html>
