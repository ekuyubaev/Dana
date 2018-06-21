<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<!-- start the middle column -->

<section>

<h2>Авторизация - Ошибка</h2>
<p>Авторизация прошла неуспешно.</p>
<p>Пожалуйста проверьте Ваш логин и пароль и повторите попытку.</p>

<form action="j_security_check" method="get">
    <label>Логин</label>
    <input type="text" name="j_username"><br>
    <label>Пароль</label>
    <input type="password" name="j_password"><br>
    <label>&nbsp;</label>
    <input type="submit" value="Login">
</form>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />