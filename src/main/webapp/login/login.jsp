<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- start the middle column -->

<section>

<h1>Форма авторизации</h1>
<p>Для продолжения введи логин и пароль.</p>
<p><i>${message}</i></p>
<form action="<c:url value='/login' />" method="post">
    <label>Логин</label>
    <input type="text" name="login"><br>
    <label>Пароль</label>
    <input type="password" name="password"><br>
    <label>&nbsp;</label>
    <input type="submit" value="Войти">
</form>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />