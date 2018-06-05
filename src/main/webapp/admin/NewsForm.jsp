<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date"/> 
    
<!-- start the middle column -->
    
<section id="edit">
<div align="center">
        <c:if test="${news != null}">
            <form action="updateNews" method="post">
        </c:if>
        <c:if test="${news == null}">
            <form action="insertNews" method="post">
        </c:if>
        <table>
            <caption>
                <h2>
                    <c:if test="${news != null}">
                        Редактирование новости
                    </c:if>
                    <c:if test="${news == null}">
                        Добавление новости
                    </c:if>
                </h2>
            </caption>
                <c:if test="${news != null}">
                    <input type="hidden" name="ID" value="<c:out value='${news.ID}' />" />
                </c:if>           
            <tr>
                <th>Дата: </th>
                <td>
                    <c:if test="${news != null}">
                        <input type="text" name="date" 
                            value="<fmt:formatDate value="${news.time}" pattern="dd.MM.yyyy HH:mm" />" readonly/>
                    </c:if>
                    <c:if test="${news == null}">
                        <input type="text" name="date" 
                            value="<fmt:formatDate value="${now}" pattern="dd.MM.yyyy HH:mm" />" readonly/>
                    </c:if>  
                </td>
            </tr>
            <tr>
                <th>Заголовок: </th>
                <td>
                    <input type="text" name="title" size="45"
                            value="<c:out value='${news.title}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Текст: </th>
                <td>
                    <textarea rows="20" cols="60" name="text"><c:if test="${news != null}">${news.text}</c:if></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Сохранить" />
                </td>
            </tr>
        </table>
        </form>
</div>
</section>   

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />