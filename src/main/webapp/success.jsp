<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.text"/>
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/basicsss.css">
</head>
<body>
    <jsp:include page="global-header.jsp"/>
    <main>
    <section class="sp"><!--Section Picture-->
        <img src="images/recovery.jpg" alt="Recovery">
    </section>
    <section><!--Section Success-->
        <div class="ss-context">
            <div class="ss-item">
                <img class="ss-sl" src="images/success.png" alt="Success">
                <span class="ss-record"><fmt:message key="success.title"/></span>
            </div>
            <div class="ss-action">
                <a class="ss-button" href="movie?command=${pageContext.session.getAttribute("command")}&movie_id=${pageContext.session.getAttribute("movie_id")}&actor_id=${pageContext.session.getAttribute("actor_id")}">
                    <fmt:message key="success.action"/>
                </a>
            </div>
        </div>
    </section>
    </main>
    <jsp:include page="global-footer.jsp"/>
</body>
</html>
