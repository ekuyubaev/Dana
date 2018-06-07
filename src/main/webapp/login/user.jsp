<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<!-- begin middle column -->

<section class="cart">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Введите Ваши данные</h1>

	<form action="<c:url value='/userController/addClient' />" method=post>

    <p id="required">Обязательные поля помечены <span class="required">*</span></p>
    <p id="required"><span class="required">${message}</span></p>
    <label>ФИО клиента</label>
    <input type="text" name="clientName" value="${client.clientName}" required>
    <p class="required">*</p><br>
    
    <label>Дата рождения</label>
    <input type="date" name="clientBirthDate" value="${client.clientBirthDate}" required><br>
    
    <label>e-mail</label>
    <input type="email" name="clientMail" value="${client.clientMail}" required>
    <p class="required">*</p><br>
    
    <label>Телефон</label>
    <input type="text" name="clientPhone" value="${client.clientPhone}" required>
    <p class="required">&nbsp;</p><br>
    
    <label>Адрес доставки</label>
    <input type="text" name="clientAdress" value="${client.clientAdress}" required> 
    <p class="required">*</p><br>
      
    <label>Логин</label>
    <input type="text" name="clientLogin" value="${client.clientLogin}" required>
    <p class="required">&nbsp;</p><br>
    
    <label>Пароль</label>
    <input type="password" name="password" value="${password}" required>
    <p class="required">*</p><br>
    
    <label>Подтверждение пароля</label>
    <input type="password" name="passwordConfirmation" value="${passwordConfirmation}" required>
    <p class="required">*</p><br>
    <label>&nbsp;</label>
    <input type="submit" value="Зарегестрироваться">
</form>
    
</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />