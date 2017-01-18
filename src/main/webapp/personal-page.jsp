<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
  <link rel="stylesheet" type="text/css" href="css/basicsss.css">
</head>
<body>
<jsp:include page="global-header.jsp"/>

<main>
  <section class="sc fade-in middle"><!--Section Create-->
    <img src="images/recovery.jpg" alt="Recovery">
    <h1 class="sc-title"><scan class="sc-title-text fade-in latest">Personal Page</scan></h1>
  </section>
  <section class="spi"><!--Section Personal Information-->
    <form action="movie" name="registration_form" method="post" onsubmit="return validate()">
      <ul class="si-list">
        <li class="si-item">
          Email address ${user.email}
        </li>
        <li class="si-item">
          First name ${user.firstName}
        </li>
        <li class="si-item">
          Last name ${user.lastName}
        </li>
        <li class="si-item">
          <button class="si-form-button" type="submit">Exit</button>
          <input type="hidden" value="logout" name="command">
        </li>
      </ul>
    </form>
  </section>
</main>

<jsp:include page="global-footer.jsp"/>
</body>
</html>
