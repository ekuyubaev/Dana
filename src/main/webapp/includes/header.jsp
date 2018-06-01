<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Филиал Фуд Мастер</title>
    <link rel="shortcut icon" href="<c:url value='/images/favicon.ico'/>">
    <link rel="stylesheet" href="<c:url value='/styles/main.css'/> ">
</head>

<body>

    <header>
        <img src="<c:url value='/images/logo.png'/>" 
             alt="Food Master Logo" width="150">
        <h1>Филиал Фуд Мастер</h1>
        <h2>Всегда свежая и качественная молочная продукция!</h2>
    </header>
    <nav id="nav_bar">
        <ul>
        	<li><a href="<c:url value='/login' />">
                    Вход в систему</a></li>
            <li><a href="<c:url value=''/>">
                    Регистрация</a></li>
            <li><a href="<c:url value='/order/showCart'/>">
                    Показать корзину</a></li>
            <li><a href="<c:url value='/forum'/>">
                    Форум</a></li>
            <li><a href="<c:url value='/order/showCart'/>">
                    Личный кабинет</a></li>
            <li><a href="<c:url value='/adminController'/>">
                    Администрирование</a></li>
        </ul>
    </nav>