<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
    
<!-- start the middle column -->
    
<section id="edit">
<div align="center">
	<c:if test="${message != null}">
	    <form action="updateMessage" method="post">
	    	<input type="hidden" name="messageID" value="<c:out value='${message.ID}' />" />
	    	<input type="hidden" name="topicID" value="<c:out value='${message.topicID}' />" />
	</c:if>
	<c:if test="${message == null}">
	    <form action="insertMessage" method="post">
	    <input type="hidden" name="topicID" value="<c:out value='${topicID}' />" />
	</c:if>
			          
	        <table>
	            <caption>
	                <h2>
	                    <c:if test="${message != null}">
	                        Редактирование сообщения
	                    </c:if>
	                    <c:if test="${message == null}">
	                        Новое сообщение
	                    </c:if>
	                </h2>
	                <i>${info}</i>
	            </caption>            
	            <tr>
	                <td>
	                    <textarea rows="20" cols="60" name="text"><c:if test="${message != null}">${message.text}</c:if></textarea> 
	                </td>
	            </tr>
	            <tr>
	                <td colspan="2" align="center">
	                    <input type="submit" value="Сохранить"/>
	                </td>
	            </tr>
	        </table>
        </form>
</div>
</section>   

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />