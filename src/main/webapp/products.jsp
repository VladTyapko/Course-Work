<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="/stylesheet">
    <script src="script/main.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="css/default-page.css" type="text/css"/>
    <link rel="stylesheet" href="css/products.css" type="text/css"/>
    <title>ShopRich</title>
</head>
<body>
<sql:setDataSource
        var="DataBase"
        driver="org.postgresql.Driver"
        url="jdbc:postgresql://localhost:5432/product_shop"
        user="postgres" password="2222"
/>
<sql:query var="list_products" dataSource="${DataBase}">
    SELECT * FROM products ORDER BY product_quantity DESC;
</sql:query>
<header id="header">
    <div class="header-main-menu">
        <a href="${pageContext.request.contextPath}/products.jsp" class="hrefs" id="header-main-menu-stripes-link"
           onClick="hide_show();return false;"><img
                src="img/main-menu/menu-stripes.webp" alt="menu" id="header-main-menu-stripes"/></a>
    </div>
    <div class="header-main-button">
        <a href="${pageContext.request.contextPath}/products" class="hrefs" id="header-main-button-url">ShopRic<i>H</i></a>
    </div>
    <div class="header-main-auth">
        <c:set value="authorization_icon" var="id"/>
        <c:choose>
            <c:when test="${sessionScope.isAuthorized!=null}">
                <!-- set var="isAuthorized" value="" scope="session"/>-->
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
<main id="main">
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
        <c:if test="${sessionScope.isManager!=null}">
            <c:if test="${sessionScope.isManager==true}">
                <div class="main-menu-list-stripes">
                    <!-- <a methods="post" href="admin-panel.jsp" class="hrefs" id="admin-panel">Admin panel</a>-->
                    <form action="${pageContext.request.contextPath}/adminForward" method="post">
                        <input type="hidden" name="chooser" value="manager_pur_history">
                        <input type="submit" value="Ready orders"
                               class="href-to-servlet-forwarder main-menu-list-stripes menu-buttons"
                               name="adminOrMain">
                    </form>
                </div>
                <div class="main-menu-list-stripes">
                    <!-- <a methods="post" href="admin-panel.jsp" class="hrefs" id="admin-panel">Admin panel</a>-->
                    <form action="${pageContext.request.contextPath}/adminForward" method="post">
                        <input type="hidden" name="chooser" value="all_pur_history">
                        <input type="submit" value="All purchases"
                               class="href-to-servlet-forwarder main-menu-list-stripes menu-buttons"
                               name="adminOrMain">
                    </form>
                </div>
            </c:if>
        </c:if>
    </div>
    <div class="main-content">
        <div class="main-content-session-h">
            <p>Products</p></div>
        <c:forEach var="product" items="${list_products.rows}">
            <div class="session">
                <div class="product-img-and-info">
                    <!-- session-image-->
                    <img class="product-image" src="${product.product_img_url}"
                         alt="product picture"/>
                    <hr/>
                    <p class="product-p"><b>Title: </b>${product.product_name}</p>
                    <hr/>
                    <p class="product-p"><b>Description: </b>${product.product_desc}</p>
                    <p class="product-p"><b>Quantity: </b>${product.product_quantity}</p>
                    <p class="product-p"><b>Product price: </b>${product.product_price} $</p>
                    <div class="main-former">
                        <form method="post" name="product-buy" action="${pageContext.request.contextPath}/buy-product"
                              class="main-content-form-buy">
                            <input type="number" id="pur_quantity" name="pur_quantity" min="1" value="0"
                                   max="${product.product_quantity}" class="product-buy-butt"/>
                            <input type="hidden" name="product_id" value="${product.product_id}">
                            <input type="hidden" name="product_quantity" value="${product.product_quantity}">
                            <input type="submit" value="to bucket"
                                   class="product-buy-submit-button">
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    </div>
</main>

<c:if test="${sessionScope.sql_exception==true}">
    <c:set var="sql_exception" scope="session" value="${false}"/>
    <script>
        window.onload = function () {
            alert('Some problem occurred! Try one more time.');
        }</script>
</c:if>
<c:if test="${sessionScope.black_list==true}">
    <c:set var="black_list" scope="session" value="${false}"/>
    <script>window.onload = function () {
        alert('You are in black list and you are not able to buy products !');
    }</script>
</c:if>
<c:if test="${sessionScope.no_goods==true}">
    <c:set var="no_goods" scope="session" value="${false}"/>
    <script>window.onload = function () {
        alert('You have no goods !');
    }</script>
</c:if>
<c:if test="${sessionScope.successful_buy==true}">
    <c:set var="successful_buy" scope="session" value="${false}"/>
    <script>
        window.onload = function () {
            alert('Successful purchase! You can see your purchases in -> "My goods"');
        }</script>
</c:if>
<!-- <a href="movies">Hello Servlet</a> -->
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