<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<!-- start the middle column -->

<!-- If necessary, this page could be generated from the database. -->

<section id="admin">
    <h1>Управление категориями продукции</h1>
    <h4>
        <a href="<c:url value='/adminController/addCategory' />">Добавить новую категорию</a> 
    </h2>
    <br>
    <h2>Перечень категорий продукции</h1>

    <table id="admintable">
	    <tr>
	        <th>Наименование категории</th>
	        <th>Примечание</th>
	        <th>Действия</th>
	    </tr>
	    <c:forEach items="${categories}" var="category" varStatus="status">
	    <tr>
	        <td>${category.categoryName}</td>
	        <td>${category.categoryNote}</td>
	        <td>
	            <a href="<c:url value='/adminController/editCategory?categoryID=${category.categoryID}' />">Редактировать</a>
	            <br>
	            <a href="<c:url value='/adminController/deleteCategory?categoryID=${category.categoryID}' />">Удалить</a>                     
            </td>
	    </tr>
	    </c:forEach>
	</table>   
</section>

<!-- end the middle column -->
<jsp:include page="/includes/footer.jsp" />