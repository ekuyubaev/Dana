<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
    
<!-- start the middle column -->
    
<section id="edit">
<div align="center">
        <c:if test="${employee != null}">
            <form action="updateEmployee" method="post">
        </c:if>
        <c:if test="${employee == null}">
            <form action="insertEmployee" method="post">
        </c:if>
        <table>
            <caption>
                <h2>
                    <c:if test="${employee != null}">
                        Редактирование данных сотрудника
                    </c:if>
                    <c:if test="${employee == null}">
                        Добавление нового сотрудника
                    </c:if>
                </h2>
            </caption>
                <c:if test="${employee != null}">
                    <input type="hidden" name="ID" value="<c:out value='${employee.employeeId}' />" />
                </c:if>           
            <tr>
                <th>ФИО сотрудника: </th>
                <td>
                    <input type="text" name="name" size="45"
                            value="<c:out value='${employee.employeeName}' />"/>
                </td>
            </tr>
            <tr>
                <th>Должность: </th>
                <td>
                    <select name="positionID">
					    <c:forEach var="position" items="${positions}">
				    		<c:if test="${position.ID == employee.positionId}">
				    			<option value="${position.ID}" selected>${position.name}</option>
				    		</c:if>
				    		<c:if test="${position.ID != employee.positionId}">
				    			<option value="${position.ID}">${position.name}</option>
				    		</c:if>
					    </c:forEach>
					</select>
                </td>
            </tr>
            <tr>
                <th>Дата рождения:</th>
                <td>
                    <input type="date" name="birthDate" size="45"
                            value="<c:out value='${employee.employeeBirthDate}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Телефон: </th>
                <td>
                    <input type="text" name="phone" size="45"
                            value="<c:out value='${employee.employeePhone}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Примечание: </th>
                <td>
                    <input type="text" name="notes" size="45"
                            value="<c:out value='${employee.employeeNotes}' />"
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