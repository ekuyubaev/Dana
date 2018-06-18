<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- begin middle column -->

<section id="admin">
<h1>Страница отчетов</h1>

<form method="post">
<table>
<tr>
	<td width="30%"><label>Начальная дата: <input type="date" name="startDate"></label></td>
	<td width="30%"><label> Конечная дата: <input type="date" name="endDate"></label></td>
	<td width="40%" valign="bottom"><input type="submit" value="Отчет по продажам"></td>
</tr>
</table>
</form>
<br>
<br>
<form method="post">
<table>
<tr>
	<td width="30%"><label>Год: <input type="number" value="2018"></label></td>
	<td width="30%">
		<label>Месяц: <select id="month" size="1" name="month" >
	      <option value="01" >Январт</option>
	      <option value="02">Февраль</option>
	      <option value="03">Март</option>
	      <option value="04">Апрель</option>
	      <option value="05">Май</option>
	      <option value="06">Июнь</option>
	      <option value="07">Июль</option>
	      <option value="08">Август</option>
	      <option value="09">Сентябрь</option>
	      <option value="10">Октябрь</option>
	      <option value="11">Ноябрь</option>
	      <option value="12">Декабрь</option>
	    </select></label>
	</td>
	<td width="40%" valign="bottom"><input type="submit" value="Ежемесячный отчет"></td>
</tr>
</table>
</form>
</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />