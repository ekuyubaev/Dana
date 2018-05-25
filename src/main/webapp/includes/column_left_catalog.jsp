<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside id="sidebarA">
    <nav>
      <ul>
      		<li><a class="current" href="<c:url value='/' />">
                  На главную</a></li>
      		<c:forEach var="category" items="${categories}" varStatus="status">
      			<li><a href="<c:url value='catalog?categoryID=${category.categoryID}' />">
                  ${category.categoryName}</a></li>
      		</c:forEach>
      </ul>
    </nav>
</aside>