<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<aside id="sidebarB">
	<c:forEach items="${newsList}" var="news" varStatus="status">
	    <tr>
	        <td>
	        	<h4><a href="<c:url value='/userController/showNews?ID=${news.ID}' />">${news.title}</a></h4>
				<p>${news.text}</p>                  
            </td>
	    </tr>
	    </c:forEach>
</aside>