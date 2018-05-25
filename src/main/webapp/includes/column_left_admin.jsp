<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside id="sidebarA">
    <nav>
      <ul>
          <li><a href="<c:url value='/' />">На главную</a></li>
          <li><a href="<c:url value='/adminController/displayCategories' />">Категории продукции</a></li>
          <li><a href="<c:url value='/adminController/displayProducts' />">Продукция</a></li>
          <li><a href="<c:url value='/adminController/displayDiscounts' />">Скидки</a></li>
          <li><a href="<c:url value='/adminController/displayOrders' />">Заказы</a></li>
          <li><a href="<c:url value='/adminController/displayStaff' />">Сотрудники</a></li>
          <li><a href="<c:url value='/adminController/displayTransport' />">Транспортные средства</a></li>
      	  <li><a href="<c:url value='/adminController/displayNews' />">Новости</a></li>
      	  <li><a href="<c:url value='/adminController/displayTitles' />">Темы форума</a></li>
      	  <li><a href="<c:url value='/adminController/displayReports' />">Отчетные документы</a></li>
      </ul>
    </nav>
</aside>