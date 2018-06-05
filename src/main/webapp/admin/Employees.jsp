<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section id="admin">
    <h1>Управление списком сотрудников</h1>
    <h4>
        <a href="<c:url value='/adminController/addEmployee' />">Добавить нового сотрудника</a> 
    </h2>
    <br>
    <h2>Список сотрудников</h1>

    <table id="admintable">
	    <tr>
	        <th>ФИО сотрудника</th>
	        <th>Должность</th>
	        <th>Дата рождения</th>
	        <th>Телефон</th>
	        <th>Примечание</th>
	        <th>Действия</th>
	    </tr>
	    <c:forEach items="${employees}" var="employee" varStatus="status">
	    <tr>
	        <td>${employee.employeeName}</td>
	        <td>
	        	<c:forEach var="position" items="${positions}">
					<c:if test="${position.ID == employee.positionId}">
						${position.name}
					</c:if>
				</c:forEach>
	        </td>
	        <td>${employee.employeeBirthDate}</td>
	        <td>${employee.employeePhone}</td>
	        <td>${employee.employeeNotes}</td>
	        <td>
	            <a href="<c:url value='/adminController/editEmployee?ID=${employee.employeeId}' />">Редактировать</a>
	            <br>
	            <a href="<c:url value='/adminController/deleteEmployee?ID=${employee.employeeId}' />">Удалить</a>                     
            </td>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->
<jsp:include page="/includes/footer.jsp" />