<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside id="sidebarA">
    <nav>
      <ul>
          <li><a class="current" href="<c:url value='/' />">
                  На главную</a></li>
          <li><a href="<c:url value='/userController/orders' />">
                  Мои заказы</a></li>
          <li><a href="<c:url value='/userController/editUser' />">
                  Настройки профиля</a></li>
      </ul>
    </nav>
</aside>