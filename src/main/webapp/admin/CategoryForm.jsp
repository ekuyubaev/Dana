<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
    
<!-- start the middle column -->
    
<section id="edit">
<div align="center">
        <c:if test="${category != null}">
            <form action="updateCategory" method="post">
        </c:if>
        <c:if test="${category == null}">
            <form action="insertCategory" method="post">
        </c:if>
        <table>
            <caption>
                <h2>
                    <c:if test="${category != null}">
                        Редактирование категории
                    </c:if>
                    <c:if test="${category == null}">
                        Добавление новой категории
                    </c:if>
                </h2>
            </caption>
                <c:if test="${category != null}">
                    <input type="hidden" name="categoryID" value="<c:out value='${category.categoryID}' />" />
                </c:if>           
            <tr>
                <th>Наименование: </th>
                <td>
                    <input type="text" name="categoryName" size="45"
                            value="<c:out value='${category.categoryName}' />"/>
                </td>
            </tr>
            <tr>
                <th>Примечание: </th>
                <td>
                    <input type="text" name="categoryNote" size="45"
                            value="<c:out value='${category.categoryNote}' />"
                    />
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