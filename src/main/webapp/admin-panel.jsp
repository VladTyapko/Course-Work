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
    <link rel="stylesheet" href="css/admin-panel.css" type="text/css"/>
    <link rel='stylesheet prefetch'
          href='https://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900&subset=latin,latin-ext'>
    <title>Admin</title>
</head>
<body>
<c:if test="${sessionScope.isAdmin==null}">
    <c:redirect url="products.jsp"/>
</c:if>
<sql:setDataSource
        var="DataBase"
        driver="org.postgresql.Driver"
        url="jdbc:postgresql://localhost:5432/product_shop"
        user="postgres" password="2222"
/>
<c:if test="${sessionScope.product_to_choose_id!=null}">
    <sql:query var="product_by_id" dataSource="${DataBase}">
        select * from products WHERE product_id=${sessionScope.product_to_choose_id};
    </sql:query>
</c:if>

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
                        <input type="hidden" name="chooser" value="main_page">
                        <input type="submit" value="Main page"
                               class="href-to-servlet-forwarder main-menu-list-stripes menu-buttons"
                               name="adminOrMain">
                    </form>
                </div>
                <div class="main-menu-list-stripes">
                    <!-- <a methods="post" href="admin-panel.jsp" class="hrefs" id="admin-panel">Admin panel</a>-->
                    <form action="${pageContext.request.contextPath}/adminForward" method="post">
                        <input type="hidden" name="chooser" value="manager_pur_history">
                        <input type="submit" value="All purchases"
                               class="href-to-servlet-forwarder main-menu-list-stripes menu-buttons"
                               name="adminOrMain">
                    </form>
                </div>
            </c:if>
        </c:if>
    </div>
    <div class="main-content">
        <div class="main-content-form">
            <h1>Add new product:</h1>
            <form method="post" action="${pageContext.request.contextPath}/add-product" name="formProductAdder">

                <label for="productAdder-productName">Enter product title: *</label>
                <input type="text" id="productAdder-productName" name="product_name" required minlength="3" maxlength="22"
                       placeholder="Title">

                <label for="productAdder-productDesc">Enter product description: *</label>
                <input type="text" id="productAdder-productDesc" name="product_desc" required
                       minlength="25" maxlength="250" placeholder="Product description">

                <label for="productAdder-productImgUrl">Enter url for product image: </label>
                <input type="url" id="productAdder-productImgUrl" name="product_img_url"
                       placeholder="Url for product image">

                <label for="productAdder-productPrice">Enter product price: *</label>
                <input type="number" id="productAdder-productPrice" required
                       name="product_price" min="0" max="100000">

                <label for="productAdder-productQuantity">Enter product quantity: *</label>
                <input type="number" id="productAdder-productQuantity" required
                       name="product_quantity" min="0">

                <input type="submit" value="Submit">
            </form>
        </div>
        <c:if test="${list_products.rowCount!=0}">
            <div class="main-content-sessions">
                <c:forEach var="product" items="${list_products.rows}">
                    <p><b>Product id:</b> ${product.product_id}</p> | <p><b>Title:</b> ${product.product_name}</p> |
                      <p><b>Price :</b> ${product.product_price}</p> | <p><b>Quantity:</b> ${product.product_quantity}</p>
                    <hr>
                </c:forEach>
            </div>
        </c:if>
        <div class="main-content-form">
            <h1>Delete products: </h1>
            <form action="${pageContext.request.contextPath}/delete-products" method="post">
                <label for="main-content-delete-product-by-id">Delete product by id: </label>
                <input id="main-content-delete-product-by-id" type="number" name="delete_product_by_id" min="0"
                       placeholder="Product id"/>
                <input type="submit" value="Delete by id">
                <input type="submit" name="submit_delete_all_products" value="Delete all products">
            </form>
        </div>
        <div class="main-content-form">
            <h1>Edit product by id: </h1>
            <form action="${pageContext.request.contextPath}/choose-product-id" method="post">
                <label for="main-content-product-id">Enter product id: </label>
                <input id="main-content-product-id" type="number" name="product_id" min="0" required
                       placeholder="Product id"/>
                <input type="submit" value="Submit" id="view-here">
            </form>
        </div>
        <c:if test="${product_by_id!=null}">
            <c:forEach var="product" items="${product_by_id.rows}">
                <div class="main-content-form">
                    <h1>Edit product: </h1>
                    <form action="${pageContext.request.contextPath}/edit-product" method="post">

                        <input type="hidden" value="${sessionScope.product_to_choose_id}" name="product_id">

                        <label for="productAdder-productNewName">Enter product title: *</label>
                        <input type="text" id="productAdder-productNewName" name="product_name" required minlength="3" maxlength="22"
                               placeholder="Title" value="${product.product_name}">

                        <label for="productAdder-productNewDesc">Enter product description: *</label>
                        <input type="text" id="productAdder-productNewDesc" name="product_desc" required
                               minlength="25" maxlength="250" value="${product.product_desc}" placeholder="Product description">

                        <label for="productAdder-productNewImgUrl">Enter url for product image: </label>
                        <input type="url" id="productAdder-productNewImgUrl" value="${product.product_img_url}" name="product_img_url"
                               placeholder="Url for product image">

                        <label for="productAdder-productNewPrice">Enter product price: *</label>
                        <input type="number" id="productAdder-productNewPrice" value="${product.product_price}" required
                               name="product_price" min="0" max="100000">

                        <label for="productAdder-productNewQuantity">Enter product quantity: *</label>
                        <input type="number" id="productAdder-productNewQuantity" value="${product.product_quantity}" required
                               name="product_quantity" min="0">

                        <input type="submit" value="Submit">
                    </form>
                </div>
            </c:forEach>
        </c:if>
        <div class="main-content-form">
            <h1>Remove user from black list: </h1>
            <form action="${pageContext.request.contextPath}/black-list" method="post">
                <label for="main-content-black-list-user-email-rm">Enter user email: </label>
                <input id="main-content-black-list-user-email-rm" type="email" name="user_email_rm_bl" required
                       placeholder="example@xxx.xxx"/>
                <input type="submit" value="Remove"/>
            </form>
        </div>
        <div class="main-content-form">
            <h1>Add user to black list: </h1>
            <form action="${pageContext.request.contextPath}/black-list" method="post">
                <label for="main-content-black-list-user-email">Enter user email: </label>
                <input id="main-content-black-list-user-email" type="email" name="user_email_to_bl" required
                       placeholder="example@xxx.xxx"/>
                <input type="submit" value="Submit"/>
            </form>
        </div>
        <div class="main-content-form">
            <h1>Delete all purchases: </h1>
            <form action="${pageContext.request.contextPath}/delete-all-purs" method="post">
                <label for="main-content-delete-all-tickets">Submit: </label>
                <input type="submit" name="delete_all_purs" id="main-content-delete-all-tickets"
                       value="Delete all purchases"/>
            </form>
        </div>
    </div>
</main>
<c:if test="${sessionScope.scroll_to_bottom==true}">
    <script> window.onload = function () {
        document.getElementById("view-here").scrollIntoView();
    } </script>
    <c:set var="scroll_to_bottom" value="${false}" scope="session"/>
</c:if>
<c:if test="${sessionScope.sql_exception==true}">
    <script>
        alert('Some problems! Try one more time.');
    </script>
    <c:set var="sql_exception" value="${false}" scope="session"/>
</c:if>
<!-- <a href="movies">Hello Servlet</a> -->
</body>
</html>