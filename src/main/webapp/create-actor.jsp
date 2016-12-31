<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
  <link rel="stylesheet" type="text/css" href="css/basic.css">
</head>
<body>
<jsp:include page="global-header.jsp"/>
<main>
  <section class="sc fade-in middle"><!--Section Create-->
    <img src="images/recovery.jpg" alt="">
    <h1 class="sc-title"><scan class="sc-title-text fade-in latest">Create Actor</scan></h1>
  </section>
  <section  class="si fade-in first"><!--Sign Input-->
    <form action="movie">
      <ul class="si-list">
        <li class="si-item">
          <input class="form-input  size-input-sign-up" type="text" name="first_name" placeholder="First name" autofocus="true" tabindex="1"/>
        </li>
        <li class="si-item">
          <input class="form-input size-input-sign-up" type="text" name="last_name" placeholder="Last name" tabindex="2">
        </li>
        <li class="si-item">
          <input class="form-input size-input-sign-up" type="text" name="biography" placeholder="Biography" tabindex="3">
        </li>
        <li class="si-item">
          <input type="file" name="photo"/>
        </li>
        <li class="si-item">
          <button class="si-form-button" type="submit">Add Actor</button>
          <input type="hidden" value="add_actor" name="command">
          <scan>${message_error}</scan>
        </li>
      </ul>
    </form>
  </section>
</main>
<jsp:include page="global-footer.jsp"/>
</body>
</html>
