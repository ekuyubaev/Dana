<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section id="admin">
    <h1>Управление новостями</h1>
    <h4>
        <a href="<c:url value='/adminController/addNews' />">Добавить новость</a> 
    </h2>
    <br>
    <h2>Перечень новостей</h1>

    <table>
	    <tr>
	        <th>Дата</th>
	        <th>Заголовок</th>
	        <th>Текст</th>
	        <th>Автор</th>
	        <th>Действия</th>        
	    </tr>
	    <c:forEach items="${newsList}" var="news" varStatus="status">
	    <tr>
	        <td><fmt:formatDate value="${news.time}" pattern="dd.MM.yyyy HH:mm" /></td>
	        <td>${news.title}</td>
	        <td>${news.text}</td>
	        <td>${news.author}</td>
	        <td>
	            <a href="<c:url value='/adminController/editNews?ID=${news.ID}' />">Редактировать</a>
	            <br>
	            <a href="<c:url value='/adminController/deleteNews?ID=${news.ID}' />">Удалить</a>                     
            </td>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->
<jsp:include page="/includes/footer.jsp" />