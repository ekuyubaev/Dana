<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<!-- start the middle column -->

<section>

<h1>Форма авторизации</h1>
<p>Для продолжения введи логин и пароль.</p>
<form action="j_security_check" method="post">
    <label>Логин</label>
    <input type="text" name="j_username"><br>
    <label>Пароль</label>
    <input type="password" name="j_password"><br>
    <label>&nbsp;</label>
    <input type="submit" value="Войти">
</form>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />