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
    <link rel="stylesheet" href="css/default-page.css" type="text/css"/>
    <link rel="stylesheet" href="css/my-goods.css" type="text/css"/>
    <link rel='stylesheet prefetch'
          href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900&subset=latin,latin-ext'>
    <title>My goods</title>
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
<sql:query var="goods" dataSource="${DataBase}">
    select * from (purchased_product left join products p on p.product_id = purchased_product.pur_product_id) where pur_buyer='${sessionScope.isAuthorized}';
</sql:query>
<c:if test="${goods.rowCount<1}">
    <c:set var="no_goods" value="${true}" scope="session"/>
    <c:redirect url="products.jsp"/>
</c:if>
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
<main id="main" onload="scrollToUserInfo()"
      style="background-image: url(https://png.pngtree.com/thumb_back/fw800/background/20190928/pngtree-background-in-the-style-of-the-80s-with-multicolored-geometric-shapes-image_318103.jpg)">
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
        <h1>Purchases</h1>
        <c:forEach var="good" items="${goods.rows}">
            <div class="good">
                <div class="back-img" style="background-image: url(${good.product_img_url})">
                    <div class="good__content">
                        <p class="good__text bolder">Title: ${good.product_name}</p>
                        <p class="good__text ">Quantity: ${good.pur_quantity}</p>
                        <p class="good__text">Purchased: ${good.pur_date_and_time}</p>
                        <p class="good__text">Price: ${good.product_price*good.pur_quantity} $</p>
                        <form action="${pageContext.request.contextPath}/clear-purchases" method="post">
                            <input type="hidden" name="good_pur_id" value="${good.pur_id}"/>
                            <input type="hidden" name="good_id_to_delete" value="${good.pur_product_id}"/>
                            <p class="good__text"><input type="submit" value="clear" class="submiters"/></p>
                        </form>
                        <form action="${pageContext.request.contextPath}/to-order" method="post">
                            <input type="hidden" name="good_pur_id" value="${good.pur_id}"/>
                            <input type="hidden" name="good_id_to_buy" value="${good.pur_product_id}"/>
                            <input type="hidden" name="good_quantity" value="${good.pur_quantity}"/>
                            <input type="hidden" name="good_price" value="${good.product_price*good.pur_quantity}"/>
                            <p class="good__text"><input type="submit" value="continue" class="submiters"/></p>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</main>
<c:if test="${sessionScope.sql_exception==true}">
    <c:set var="sql_exception" scope="session" value="${false}"/>
    <script>
        window.onload = function () {
            alert('Some problem occurred! Try one more time.');
        }</script>
</c:if>
<c:if test="${requestScope.order_not_available==true}">
    <c:set var="order_not_available" scope="request" value="${false}"/>
    <script>
        window.onload = function () {
            alert('You can\'t do it now, wait!');
        }</script>
</c:if><c:if test="${sessionScope.order_in_proc==true}">
    <c:set var="order_in_proc" scope="session" value="${false}"/>
    <script>
        window.onload = function () {
            alert('You order will be checked soon, wait email)');
        }</script>
</c:if>
<footer>
    <p class="footer-p" id="logo">ShopRic<i>H</i></p>
    <p class="footer-sign">created by me</p>
    <div><img alt="popcorn"
              src="https://png.pngitem.com/pimgs/s/246-2468991_offer-buy-sale-label-ribbon-tag-free-off.png"
              id="footer-img">
    </div>
    <a class="footer-hrefs" href="mailto:shoprich3@gmail.com">Send email</a>
    <a class="footer-hrefs" href="tel:+380681400449">Phone</a>
    <a class="footer-hrefs" href="https://t.me/Vladtiapko">Telegram</a>
</footer>
</body>
</html>