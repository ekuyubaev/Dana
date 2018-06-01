<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section id="forum">
    <h1>Форум</h1>
    <table>
	    <tr>
	        <th class="theme">Тема</th>
	        <th>Действия</th>
	    </tr>
	    <c:forEach items="${topics}" var="topic" varStatus="status">
	    <tr>
	        <td>${topic.name}</td>
	        <td>
	            <a href="<c:url value='/forumController/enterTopic?topicID=${topic.ID}' />">Присоединиться</a>                   
            </td>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->
<jsp:include page="/includes/footer.jsp" />