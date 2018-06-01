<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
    
<!-- start the middle column -->
    
<section id="edit">
<div align="center">
        <c:if test="${topic != null}">
            <form action="updateTopic" method="post">
        </c:if>
        <c:if test="${topic == null}">
            <form action="insertTopic" method="post">
        </c:if>
        <table>
            <caption>
                <h2>
                    <c:if test="${topic != null}">
                        Редактирование темы
                    </c:if>
                    <c:if test="${topic == null}">
                        Добавление новой темы
                    </c:if>
                </h2>
            </caption>
                <c:if test="${topic != null}">
                    <input type="hidden" name="topicID" value="<c:out value='${topic.ID}' />" />
                </c:if>           
            <tr>
                <th>Тема: </th>
                <td>
                    <input type="text" name="topicName" size="45"
                            value="<c:out value='${topic.name}' />"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Сохранить"/>
                </td>
            </tr>
        </table>
        </form>
</div>
</section>   

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />