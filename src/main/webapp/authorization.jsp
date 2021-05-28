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
    <link rel="stylesheet" href="css/form-auth.css" type="text/css"/>
    <title>Authorization</title>
</head>
<body style="background-image: url(https://lh4.googleusercontent.com/-XplyTa1Za-I/VMSgIyAYkHI/AAAAAAAADxM/oL-rD6VP4ts/w1184-h666/Android-Lollipop-wallpapers-Google-Now-Wallpaper-2.png)">
<header id="header">
    <div class="header-main-button">
        <a href="${pageContext.request.contextPath}/products.jsp" class="hrefs"
           id="header-main-button-url">ShopRic<i>H</i></a>
    </div>
</header>

<div class="materialContainer">

    <div class="box">

        <div class="title">LOGIN</div>
        <form id="authorization-form" method="post" action="${pageContext.request.contextPath}/authorization">
            <div class="input">
                <label for="user-email">E-mail</label>
                <input type="email" name="user-email" id="user-email" required>
                <span class="spin"></span>
            </div>

            <div class="input">
                <label for="user-password">Password</label>
                <input type="password" name="user-password" id="user-password" minlength="8" required>
                <span class="spin"></span>
            </div>

            <div class="button login">

                <button type="submit" form="authorization-form" value="Submit"><span>GO</span> <i
                        class="fa fa-check"></i></button>

            </div>
        </form>
        <a href="remind-password.jsp" class="pass-forgot">Forgot your password?</a>

    </div>

    <div class="overbox">
        <div class="material-button alt-2" onClick="changePhone();return false;"><span class="shape"></span></div>

        <div class="title">REGISTER</div>
        <form id="registration-form" action="${pageContext.request.contextPath}/registration" method="post">
            <div class="input">
                <label for="reg-name">Your name</label>
                <input type="text" name="reg-name" id="reg-name" minlength="3" required>
                <span class="spin"></span>
            </div>
            <div class="input">
                <label for="reg-email">E-mail</label>
                <input type="email" name="reg-email" id="reg-email" required>
                <span class="spin"></span>
            </div>
            <div class="input">
                <label for="reg-phone">Phone</label>
                <input type="tel" name="reg-phone" id="reg-phone" required>
                <span class="spin"></span>
            </div>

            <div class="input">
                <label for="reg-pass">Password</label>
                <input type="password" name="reg-pass" id="reg-pass" minlength="8" required>
                <span class="spin"></span>
            </div>

        </form>

        <div class="button">
            <button type="submit" form="registration-form" value="Submit">
                <span>NEXT</span>
            </button>
        </div>


    </div>

</div>
<c:set var="lol" value="${sessionScope.values()}"/>
<c:if test="${sessionScope.tryOtherEmail!=null}">
    <c:if test="${sessionScope.tryOtherEmail==true}">
        <c:set var="tryOtherEmail" value="${false}" scope="session"/>
        <script> window.onload = function () {
            alert('E-mail that you have entered  already exists !\nTry another one or authorize.');
        }</script>
    </c:if></c:if>
<c:if test="${sessionScope.emailNotFound!=null}">
    <c:if test="${sessionScope.emailNotFound==true}">
        <c:set var="emailNotFound" value="${false}" scope="session"/>
        <script> window.onload = function () {
            alert('E-mail that you have entered doesn\'t exist !\nTry another one or register.');
        }</script>
    </c:if>
</c:if>
<c:if test="${sessionScope.passwordIsWrong!=null}">
    <c:if test="${sessionScope.passwordIsWrong==true}">
        <c:set var="passwordIsWrong" value="${false}" scope="session"/>
        <script>window.onload = function () {
            alert('Password that you have entered is wrong !\nTry another one or tap on \'Forgot your password?\'.');
        }</script>
    </c:if>
</c:if>
<c:if test="${sessionScope.successful_remind!=null}">
    <c:if test="${sessionScope.successful_remind==true}">
        <c:set var="successful_remind" value="${false}" scope="session"/>
        <script>window.onload = function () {
            alert('Your password is successfully sent !');
        }</script>
    </c:if>
</c:if>
<c:if test="${sessionScope.wait_for_remind!=null}">
    <c:if test="${sessionScope.wait_for_remind==true}">
        <c:set var="wait_for_remind" value="${false}" scope="session"/>
        <script>window.onload = function () {
            alert('Wait 10 minutes to send password again !');
        }</script>
    </c:if>
</c:if>
</body>
</html>
