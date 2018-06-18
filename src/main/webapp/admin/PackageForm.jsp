<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
    
<!-- start the middle column -->
    
<section id="edit">
<div align="center">
        <c:if test="${p != null}">
            <form action="updatePackage" method="post">
        </c:if>
        <c:if test="${p == null}">
            <form action="insertPackage" method="post">
        </c:if>
        <table>
            <caption>
                <h2>
                    <c:if test="${p != null}">
                        Редактирование вида упаковки
                    </c:if>
                    <c:if test="${p == null}">
                        Добавление нового вида упаковки
                    </c:if>
                </h2>
            </caption>
                <c:if test="${p != null}">
                    <input type="hidden" name="ID" value="<c:out value='${p.ID}' />" />
                </c:if>           
            <tr>
                <th>Наименование: </th>
                <td>
                    <input type="text" name="name" size="45"
                            value="<c:out value='${p.name}' />"/>
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