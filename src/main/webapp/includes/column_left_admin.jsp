<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside id="sidebarA">
    <nav>
      <ul>
          <li><a href="<c:url value='/' />">На главную</a></li>
          <li><a href="<c:url value='/adminController/displayClients' />">Клиенты</a></li>
          <li><a href="<c:url value='/adminController/displayCategories' />">Категории продукции</a></li>
          <li><a href="<c:url value='/adminController/displayProducts' />">Продукция</a></li>
          <li><a href="<c:url value='/adminController/displayDiscounts' />">Скидки</a></li>
          <li><a href="<c:url value='/adminController/displayOrders' />">Заказы</a></li>
          <li><a href="<c:url value='/adminController/displayTransports' />">Транспортные средства</a></li>
      	  <li><a href="<c:url value='/adminController/displayPlan' />">План поставок</a></li>
      	  <li><a href="<c:url value='/adminController/displayReports' />">Отчетные документы</a></li>
      </ul>
    </nav>
</aside>