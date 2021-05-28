<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Tickets</title>
</head>
<body>
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
    </div>
    <div class="main-content">
        <div class="container">
            <div class="container-top-div" style="background-image: url(${requestScope.product_img_url})">
                <p class="container-top-div-p" id="first-p"><b>Product: </b>${requestScope.product_name}</p>
                <p class="container-top-div-p"><b>Order quantity: </b>${requestScope.good_quantity}</p>
                <p class="container-top-div-p"><b>Order price: </b>${requestScope.product_price} $</p>
            </div>
            <div class="main-content-form">
                <h1>Make an order:</h1>
                <form method="post" action="${pageContext.request.contextPath}/make-order">

                    <label for="fName">Enter your first name: *</label>
                    <input type="text" id="fName" name="fName" required minlength="3" maxlength="15"
                           placeholder="First name">

                    <label for="sName">Enter your second name: *</label>
                    <input type="text" id="sName" name="sName" required minlength="3" maxlength="20"
                           placeholder="Second name">

                    <label for="email">Enter your email: *</label>
                    <input type="email" id="email" name="email" required
                           placeholder="xxx@xxx.xxx">

                    <label for="phone">Enter your phone number: *</label>
                    <input type="tel" id="phone" name="phone" required
                           placeholder="+00-000-000-00-00">

                    <label for="address">Enter your address: *</label>
                    <input type="text" id="address" name="address" required minlength="20" maxlength="220"
                           placeholder="Country, city, post index, street, house, flat">
                    <c:if test="${sessionScope.promocode!=null}">
                        <c:if test="${sessionScope.promocode==true}">
                        <label  for="promocode">Do you want to use promocode 15% (Valid until you sign out): </label>
                        <input type="checkbox" id="promocode" name="promocode" value="promocode">
                        </c:if>
                    </c:if>
                    <input type="hidden" name="pur_price" value="${requestScope.product_price}">
                    <input type="hidden" name="pur_id" value="${sessionScope.order_pur_id}"/>
                    <input type="submit" value="Continue" class="product-buy-submit-button">
                </form>
            </div>
        </div>
    </div>
</main>
<footer>
    <p class="footer-p" id="logo">ShopRic<i>h</i></p>
    <p class="footer-sign">created by YH</p>
    <div><img alt="popcorn" src="https://pngimg.com/uploads/popcorn/popcorn_PNG12.png" id="footer-img">
    </div>
    <a class="footer-hrefs" href="mailto:ttatta3adpot@gmail.com">Send email</a>
    <a class="footer-hrefs" href="tel:+380681400449">Phone</a>
    <a class="footer-hrefs" href="https://t.me/shut_uper">Telegram</a>
</footer>
</body>
</html>
