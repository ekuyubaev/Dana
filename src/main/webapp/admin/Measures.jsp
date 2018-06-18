<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section id="admin">
    <h1>Управление единицами измерений</h1>
    <h4>
        <a href="<c:url value='/adminController/addMeasure' />">Добавить новую единицу измерения</a> 
    </h2>
    <br>
    <h2>Перечень единиц измерения</h1>

    <table id="admintable">
	    <tr>
	        <th>Наименование единицы измерения</th>
	        <th>Действия</th>
	    </tr>
	    <c:forEach items="${measures}" var="measure" varStatus="status">
	    <tr>
	        <td>${measure.measureName}</td>
	        <td>
	            <a href="<c:url value='/adminController/editMeasure?measureID=${measure.measureID}' />">Редактировать</a>
	            <br>
	            <a href="<c:url value='/adminController/deleteMeasure?measureID=${measure.measureID}' />">Удалить</a>                     
            </td>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->
<jsp:include page="/includes/footer.jsp" />