package com.shoprich.ShopRich.controller;

import com.shoprich.ShopRich.database.SQLrequests;
import com.shoprich.ShopRich.model.Product;
import com.shoprich.ShopRich.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "to-order", value = "/to-order")
public class ForwardToOrderServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("good_pur_id") != null && !request.getParameter("good_pur_id").equals("")) {
            int purId = Integer.parseInt(request.getParameter("good_pur_id"));
            int productId = Integer.parseInt(request.getParameter("good_id_to_buy"));
            SQLrequests sqLrequests = new SQLrequests();
            boolean res = false;
            try {
                res = sqLrequests.isPurDone(purId);
            } catch (SQLException ignored) {
            }
            if (res) {
                request.setAttribute("order_not_available", true);
                request.getRequestDispatcher("/my-goods.jsp").forward(request, response);
                return;
            }
            Product product = new Product();
            try {
                product = sqLrequests.getInfoAboutProduct(productId);
            } catch (Exception exception) {
                request.getSession().setAttribute("sql_exception", true);
                request.getRequestDispatcher("/my-goods.jsp").forward(request, response);
                return;
            }
            if(product==null) {
                request.getSession().setAttribute("sql_exception", true);
                request.getRequestDispatcher("/my-goods.jsp").forward(request, response);
                return;
            }
            assert product != null;
            request.setAttribute("good_quantity", request.getParameter("good_quantity"));
            request.setAttribute("product_name", product.getProductName());
            request.setAttribute("product_img_url", product.getProductImgUrl());
            request.setAttribute("product_price", Integer.parseInt(request.getParameter("good_price")));
            request.getSession().setAttribute("order_pur_id", purId);
            request.getRequestDispatcher("/do-order.jsp").forward(request, response);
        } else request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
}
