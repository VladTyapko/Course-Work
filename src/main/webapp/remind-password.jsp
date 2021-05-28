<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 19.04.2021
  Time: 0:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    <script src="script/auth-form.js"></script>
    <script src="script/main.js"></script>
    <meta charset="utf-8"/>
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
    <link rel='stylesheet prefetch'
          href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900&subset=latin,latin-ext'>
    <link rel="stylesheet" href="css/default-page.css" type="text/css"/>
    <link rel="stylesheet" href="css/remind-password.css" type="text/css"/>
    <title>Remind password</title>
</head>
<body>
<header id="header">
    <div class="header-main-button">
        <a href="${pageContext.request.contextPath}/products.jsp" class="hrefs"
           id="header-main-button-url">Gornite<i>X</i></a>
    </div>
</header>
<div id="form-to-remind-password">
    <form method="post" action="${pageContext.request.contextPath}/remind-password">
        <label for="email_to_send_password">Enter your email: *</label>
        <input id="email_to_send_password" type="email" required name="email_to_send_password"
               placeholder="example@xxx.xxx"/>
        <input type="submit" value="Send"/>
    </form>
</div>
<c:if test="${sessionScope.no_user!=null}">
    <c:if test="${sessionScope.no_user==true}">
        <c:set var="no_user" value="${false}" scope="session"/>
        <script>window.onload = function () {
            alert('No user with such email !');
        }</script>
    </c:if>
</c:if>
<c:if test="${sessionScope.sql_exception!=null}">
    <c:if test="${sessionScope.sql_exception==true}">
        <c:set var="sql_exception" value="${false}" scope="session"/>
        <script>window.onload = function () {
            alert('Something wrong happened! Please, check input data and try one more time.');
        }</script>
    </c:if>
</c:if>
</body>
</html>
