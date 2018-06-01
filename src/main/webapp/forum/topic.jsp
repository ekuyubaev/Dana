<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section id="forum">
    <h1>${topic.name}</h1>
    <i>${info}</i>
    <br>
    <a href="<c:url value='/forumController/addMessage?topicID=${topic.ID}' />">Добавить сообщение</a>
    <br>
    <table id="messageTable">
	    <c:forEach items="${topic.messages}" var="message" varStatus="status">
	    <tr>
	        <th>
	        	Автор: ${message.userLogin}
	        	<br><br>
	        	<a href="<c:url value='/forumController/editMessage?messageID=${message.ID}' />">Редактировать</a>
	        </th>
	        <td>${message.text}</td>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->
<jsp:include page="/includes/footer.jsp" />