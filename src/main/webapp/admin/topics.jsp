<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section id="admin">
    <h2>Управление темами форума</h2>
    <h4>
        <a href="<c:url value='/adminController/addTopic' />">Добавить новую тему</a> 
    </h4>
    <br>
    <h3>Перечень тем</h3>

    <table id="admintable">
	    <tr>
	        <th>Тема</th>
	        <th>Действия</th>
	    </tr>
	    <c:forEach items="${topics}" var="topic" varStatus="status">
	    <tr>
	        <td>${topic.name}</td>
	        <td>
	            <a href="<c:url value='/adminController/editTopic?topicID=${topic.ID}' />">Редактировать</a>
	            <br>
	            <a href="<c:url value='/adminController/deleteTopic?topicID=${topic.ID}' />">Удалить</a>                     
            </td>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->
<jsp:include page="/includes/footer.jsp" />