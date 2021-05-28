<%--
  Created by IntelliJ IDEA.
  User: Егор
  Date: 28.04.2021
  Time: 22:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <script src="script/main.js"></script>
    <meta charset="utf-8"/>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/default-page.css" type="text/css"/>
    <link rel="stylesheet" href="css/my-account.css" type="text/css"/>
    <link rel='stylesheet prefetch'
          href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900&subset=latin,latin-ext'>
    <title>My account</title>
</head>
<body>
<c:if test="${sessionScope.isAuthorized==null}">
    <c:redirect url="authorization.jsp"/>
</c:if>
<sql:setDataSource
        var="DataBase"
        driver="org.postgresql.Driver"
        url="jdbc:postgresql://localhost:5432/product_shop"
        user="postgres" password="2222"
/>
<sql:query var="user_by_email" dataSource="${DataBase}">
    SELECT * FROM users WHERE user_email='${sessionScope.isAuthorized}';
</sql:query>
<header id="header">
    <div class="header-main-menu">
        <a href="#" class="hrefs" id="header-main-menu-stripes-link"
           onClick="hide_show();return false;"><img
                src="img/main-menu/menu-stripes.webp" alt="menu" id="header-main-menu-stripes"/></a>
    </div>
    <div class="header-main-button">
        <a href="${pageContext.request.contextPath}/products.jsp" class="hrefs"
           id="header-main-button-url">ShopRic<i>H</i></a>
    </div>
    <div class="header-main-auth">
        <c:choose>
            <c:when test="${sessionScope.isAuthorized!=null}">
                <c:set value="authorization_icon" var="id"/>
                <!-- var="isAuthorized" value="" scope="session"/>-->
                <a href="${pageContext.request.contextPath}/exit" class="hrefs" id="header-main-auth-font">
                    <img src="img/main-menu/authorization_icon.jpg" alt="auth icon" id="${id}">
                    exit</a>
            </c:when>
            <c:otherwise>
                <a href="authorization.jsp" class="hrefs" id="header-main-auth-font">
                    <img src="img/main-menu/authorization_icon.jpg" alt="auth icon" id="${id}">
                    auth</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>
<main id="main" onload="scrollToUserInfo()" style=" background: url(https://wallpaperaccess.com/full/2825710.gif);background-size: cover;">
    <div id="main-menu-list" style="display: none">
        <div class="main-menu-list-stripes">
            <form action="${pageContext.request.contextPath}/adminForward" method="post">
                <input type="hidden" name="chooser" value="my_account">
                <input type="submit" value="My account"
                       class="href-to-servlet-forwarder main-menu-list-stripes menu-buttons"
                       name="adminOrMain">
            </form>
        </div>
        <div class="main-menu-list-stripes">
            <form action="${pageContext.request.contextPath}/adminForward" method="post">
                <input type="hidden" name="chooser" value="my_goods">
                <input type="submit" value="My goods"
                       class="href-to-servlet-forwarder main-menu-list-stripes menu-buttons"
                       name="adminOrMain">
            </form>
        </div>
        <c:if test="${sessionScope.isAdmin!=null}">
            <c:if test="${sessionScope.isAdmin==true}">
                <div class="main-menu-list-stripes">
                    <!-- <a methods="post" href="admin-panel.jsp" class="hrefs" id="admin-panel">Admin panel</a>-->
                    <form action="${pageContext.request.contextPath}/adminForward" method="post">
                        <input type="hidden" name="chooser" value="admin_panel">
                        <input type="submit" value="Admin panel"
                               class="href-to-servlet-forwarder main-menu-list-stripes menu-buttons"
                               name="adminOrMain">
                    </form>
                </div>
            </c:if>
        </c:if>
    </div>
    <div class="main-content">
        <c:forEach var="user" items="${user_by_email.rows}">
            <div class="main-content-user">
                <div class="user-img-and-info">
                    <img class="user-image" src="${pageContext.request.contextPath}/img/user-account/acc-icon.jpg"
                         alt="user picture"/>
                    <hr/>
                    <p class="main-content-user-p" style="font-size: 1.2em"><b>${user.user_name}</b></p>
                    <hr/>
                    <p class="main-content-user-p"><b>Email: </b>${user.user_email}</p>
                    <p class="main-content-user-p"><b>Phone: </b>${user.user_phone}</p>
                    <p class="main-content-user-p"><b>Password: </b>${user.user_pass}</p>
                </div>
                <hr style="margin-top: 40px"/>
                <form action="${pageContext.request.contextPath}/user-change" method="post" class="forms">
                    <input type="hidden" name="user_id" value="${user.user_id}">
                    <label for="user_new_name">Enter new name: </label>
                    <input type="text" id="user_new_name" minlength="3" maxlength="25" placeholder="New name"
                           name="user_new_name">
                    <label for="user_new_phone">Enter new phone: </label>
                    <input type="tel" id="user_new_phone"  placeholder="+000-00-000-00-00"
                           name="user_new_phone">
                    <label for="user_new_password">Enter new password: </label>
                    <input type="text" id="user_new_password" minlength="8" maxlength="16" placeholder="********"
                           name="user_new_password">
                    <input type="submit" value="Change">
                </form>
            </div>
        </c:forEach>
    </div>
</main>
<footer>
    <p class="footer-p" id="logo">ShopRic<i>H</i></p>
    <p class="footer-sign">created by me</p>
    <div><img alt="popcorn" src="https://png.pngitem.com/pimgs/s/246-2468991_offer-buy-sale-label-ribbon-tag-free-off.png" id="footer-img">
    </div>
    <a class="footer-hrefs" href="mailto:shoprich3@gmail.com">Send email</a>
    <a class="footer-hrefs" href="tel:+380681400449">Phone</a>
    <a class="footer-hrefs" href="https://t.me/Vladtiapko">Telegram</a>
</footer>
</body>
</html>