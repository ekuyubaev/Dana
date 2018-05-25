<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
    
<!-- start the middle column -->
    
<section id="edit">
<div align="center">
        <c:if test="${discount != null}">
            <form action="updateDiscount" method="post">
        </c:if>
        <c:if test="${discount == null}">
            <form action="insertDiscount" method="post">
        </c:if>
        <table>
            <caption>
                <h2>
                    <c:if test="${discount != null}">
                        Редактирование скидки
                    </c:if>
                    <c:if test="${discount == null}">
                        Добавление новой скидки
                    </c:if>
                </h2>
            </caption>
                <c:if test="${discount != null}">
                    <input type="hidden" name="discountID" value="<c:out value='${discount.discountID}' />" />
                </c:if>           
            <tr>
                <th>Описание: </th>
                <td>
                    <input type="text" name="discountName" size="45"
                            value="<c:out value='${discount.discountName}' />"/>
                </td>
            </tr>
            <tr>
                <th>Размер: </th>
                <td>
                    <input type="text" name="discountAmount" size="45"
                            value="<c:out value='${discount.discountAmount}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Начало: </th>
                <td>
                    <input type="date" name="discountStart" 
                            value="<c:out value='${discount.discountStart}' />"/>
                </td>
            </tr>
            <tr>
                <th>Окончание: </th>
                <td>
                    <input type="date" name="discountEnd"
                            value="<c:out value='${discount.discountEnd}' />"
                    />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Сохранить" />
                </td>
            </tr>
        </table>
        </form>
</div>
</section>   

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />