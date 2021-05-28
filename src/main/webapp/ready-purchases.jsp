<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<html>
<head>
    <script src="script/main.js"></script>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link rel='stylesheet prefetch'
          href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900&subset=latin,latin-ext'>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="css/default-page.css" type="text/css"/>
    <link rel="stylesheet" href="css/all-purchases.css" type="text/css"/>
    <title>Ready orders</title>
</head>
<body>
<c:if test="${sessionScope.isManager==null}">
    <c:redirect url="products.jsp"/>
</c:if>
<sql:setDataSource
        var="DataBase"
        driver="org.postgresql.Driver"
        url="jdbc:postgresql://localhost:5432/product_shop"
        user="postgres" password="2222"
/>
<sql:query var="list_purs" dataSource="${DataBase}">
    SELECT * FROM (pur_process pu RIGHT JOIN purchased_product pp ON pu.purchase_id=pp.pur_id) where pur_status='ready' ORDER BY pur_id;
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
<main id="main-ticket-buy">
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
        <c:forEach var="pur" items="${list_purs.rows}">
            <div class="container">
                <p class="purchases-history"><b>Purchase process id: </b>${pur.pur_process_id} <b>Product
                    id: </b>${pur.pur_product_id}</p>
                <p class="purchases-history"><b>Buyer email: </b>${pur.pur_buyer_email}</p>
                <p class="purchases-history"><b>Was done: </b>${pur.pur_date_and_time}
                    <b>Quantity: </b>${pur.pur_quantity}</p>
                <p class="purchases-history"><b>Phone: </b>${pur.pur_buyer_phone}<b> Order price: </b>${pur.pur_price}$</p>
                <p class="purchases-history"><b>First Name: </b>${pur.pur_buyer_fName}<b> Second name: </b>${pur.pur_buyer_sName}</p>
                <p class="purchases-history"><b>Address: </b>${pur.pur_buyer_address}</p>
                <form method="post" action="${pageContext.request.contextPath}/manage-order">
                    <input type="hidden" name="pur_process_id" value="${pur.pur_process_id}">
                    <input type="hidden" name="purchase_id" value="${pur.purchase_id}">
                    <input type="submit" name="finish_order" value="Finish">
                </form>
            </div>
        </c:forEach>
    </div>
</main>
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
