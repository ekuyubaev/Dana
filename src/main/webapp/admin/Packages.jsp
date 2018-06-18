<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section id="admin">
    <h1>Управление видами упаковок</h1>
    <h4>
        <a href="<c:url value='/adminController/addPackage' />">Добавить новый вид упаковки</a> 
    </h2>
    <br>
    <h2>Перечень видов упаковок</h1>

    <table id="admintable">
	    <tr>
	        <th>Наименование вида упаковки</th>
	        <th>Действия</th>
	    </tr>
	    <c:forEach items="${packages}" var="p" varStatus="status">
	    <tr>
	        <td>${p.name}</td>
	        <td>
	            <a href="<c:url value='/adminController/editPackage?ID=${p.ID}' />">Редактировать</a>
	            <br>
	            <a href="<c:url value='/adminController/deletePackage?ID=${p.ID}' />">Удалить</a>                     
            </td>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->
<jsp:include page="/includes/footer.jsp" />