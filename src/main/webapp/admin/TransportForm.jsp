<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
    
<!-- start the middle column -->
    
<section id="edit">
<div align="center">
        <c:if test="${transport != null}">
            <form action="updateTransport" method="post">
        </c:if>
        <c:if test="${transport == null}">
            <form action="insertTransport" method="post">
        </c:if>
        <table>
            <caption>
                <h2>
                    <c:if test="${transport != null}">
                        Редактирование транспорта
                    </c:if>
                    <c:if test="${transport == null}">
                        Добавление нового транспорта
                    </c:if>
                </h2>
            </caption>
                <c:if test="${transport != null}">
                    <input type="hidden" name="ID" value="<c:out value='${transport.ID}' />" />
                </c:if>           
            <tr>
                <th>Модель ТС: </th>
                <td>
                    <input type="text" name="model" size="45"
                            value="<c:out value='${transport.model}' />"/>
                </td>
            </tr>
            <tr>
                <th>Номер ТС: </th>
                <td>
                    <input type="text" name="number" size="45"
                            value="<c:out value='${transport.number}' />"/>
                </td>
            </tr>
            <tr>
                <th>Грузоподъемность ТС, кг: </th>
                <td>
                    <input type="text" name="capacity" size="45"
                            value="<c:out value='${transport.capacity}' />"/>
                </td>
            </tr>
            <tr>
            	<th>Холодильная: </th>
            	<td>
            	<input name="fridge" type="checkbox"<c:if test="${transport.fridge}"> checked</c:if>/>
            	</td>
            </tr>
            <tr>
                <th>Примечание: </th>
                <td>
                    <input type="text" name="notes" size="45"
                            value="<c:out value='${transport.notes}' />"
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