<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
    
<!-- start the middle column -->
    
<section id="edit">
<div align="center">
        <c:if test="${measure != null}">
            <form action="updateMeasure" method="post">
        </c:if>
        <c:if test="${measure == null}">
            <form action="insertMeasure" method="post">
        </c:if>
        <table>
            <caption>
                <h2>
                    <c:if test="${measure != null}">
                        Редактирование единицы измерения
                    </c:if>
                    <c:if test="${measure == null}">
                        Добавление новой единицы измерения
                    </c:if>
                </h2>
            </caption>
                <c:if test="${measure != null}">
                    <input type="hidden" name="measureID" value="<c:out value='${measure.measureID}' />" />
                </c:if>           
            <tr>
                <th>Наименование: </th>
                <td>
                    <input type="text" name="measureName" size="45"
                            value="<c:out value='${measure.measureName}' />"/>
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