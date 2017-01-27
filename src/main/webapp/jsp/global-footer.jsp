<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<footer class="gf fade-in latest"><!--Global Footer-->
  <div class="gf-context">
    <div class="gf-copyright">
      <scan class="gf-ct"><fmt:message key="gf.copyright"/></scan>
    </div>
    <div class="gf-locale">
      <a href="movie?command=change_language&locale=en_US"><img class="gf-lp" src="../images/locale_US.png" alt="Country"></a>
      <a href="movie?command=change_language&locale=ru_RU"><img class="gf-lp" src="../images/locale_RU.png" alt="Country"></a>
    </div>
  </div>
</footer>