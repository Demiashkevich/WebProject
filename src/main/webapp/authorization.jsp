<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/basic.css">
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
  <section class="sc fade-in middle"><!--Section Create-->
    <img src="images/recovery.jpg" alt="">
    <h1 class="sc-title"><scan class="sc-title-text fade-in latest">Enter Your Account</scan></h1>
  </section>
  <section  class="si fade-in first"><!--Section Input-->
    <form method="get" action="movie">
      <ul class="si-list">
        <li class="si-item">
          ${message_error}
        </li>
        <li class="si-item">
          <input class="form-input" type="text" name="email" required="true" placeholder="Email Address" autofocus="true" tabindex="1" autocomplete="false"/>
        </li>
        <li class="si-item">
          <input class="form-input" type="password" name="password" required="true" placeholder="Password" tabindex="2" autocomplete="false"/>
        </li>
        <li class="si-item">
          <a class="si-fc-link si-link-sign" href="registration.jsp">Sign Up</a>
          <button class="si-fc-button si-form-button" type="submit">Sign In</button>
          <input type="hidden" name="command" value="sign_in">
        </li>
      </ul>
    </form>
  </section>
</main>
<jsp:include page="global-footer.jsp"/>
</body>
</html>
