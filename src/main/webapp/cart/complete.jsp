<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_all.jsp" />

<!-- begin middle column -->

<section>

<h1>Спасибо, ${client.clientName}</h1>

<!-- store email address as a global variable and use EL to display it -->

<p>${message}</p>
<br>
<p>Если у Вас есть какие-либо вопросы, Вы можете связаться с нами по адресу:  
<a href="mailto:${custServEmail}">${custServEmail}</a></p>

</section>

<!-- end middle column -->

<jsp:include page="/includes/column_right_news.jsp" />
<jsp:include page="/includes/footer.jsp" />