<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside id="sidebarB">
	<c:forEach items="${newsList}" var="news" varStatus="status">
	    <tr>
	        <td><fmt:formatDate value="${news.time}" pattern="dd.MM.yyyy HH:mm" /></td>
	        <td>${news.title}</td>
	        <td>
	            <a href="<c:url value='/adminController/editNews?ID=${news.ID}' />">Ghjxbnfnm</a>                    
            </td>
	    </tr>
	    </c:forEach>
</aside>