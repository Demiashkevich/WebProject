<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="css/basic.css">
    <script type="text/javascript" src="js/function.js"></script>
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
  <section class="sc fade-in middle"><!--Section Create-->
    <img src="images/recovery.jpg" alt="">
    <h1 class="sc-title"><scan class="sc-title-text fade-in latest">Create Your Account</scan></h1>
  </section>
  <section  class="si fade-in first"><!--Sign Input-->
    <form action="movie" name="registration_form" method="post" onsubmit="return validate()">
      <ul class="si-list">
        <li class="si-item">
          <input class="form-input  size-input-sign-up" value="${email}" type="email" name="email" placeholder="name@example.com" autofocus="false" tabindex="1"/>
        </li>
        <li class="si-item">
          <input class="form-input size-input-sign-up" value="${password}" type="password" name="password" placeholder="password" tabindex="2">
        </li>
        <li class="si-item">
          <input class="form-input size-input-sign-up" value="${password}" type="password" name="password_confirm" placeholder="confirm password" tabindex="3">
        </li>
        <li class="si-item">
          <input class="form-input size-input-sign-up" value="${first_name}" type="text" name="first_name" placeholder="first name" tabindex="4"/>
        </li>
        <li class="si-item">
          <input class="form-input size-input-sign-up" value="${last_name}" type="text" name="last_name" placeholder="last name" tabindex="5"/>
        </li>
        <li class="si-item">
          <button class="si-form-button" type="submit">Create</button>
          <input type="hidden" value="sign_up" name="command">
          <scan>${message_error}</scan>
        </li>
      </ul>
    </form>
  </section>
</main>
<jsp:include page="global-footer.jsp"/>
</body>
</html>
