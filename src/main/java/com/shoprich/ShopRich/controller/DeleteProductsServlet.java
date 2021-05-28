package com.shoprich.ShopRich.controller;

import com.shoprich.ShopRich.database.SQLrequests;
import com.shoprich.ShopRich.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "delete-products", value = "/delete-products")
public class DeleteProductsServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (User.getAdminsEmails().contains((String) request.getSession().getAttribute("isAuthorized"))) {
            SQLrequests sqLrequests = new SQLrequests();
            if (request.getParameter("submit_delete_all_products") != null
                    && request.getParameter("submit_delete_all_products").equals("Delete all products")) {
                try {
                    sqLrequests.deleteAllProducts();
                } catch (SQLException ignored) {
                    request.getSession().setAttribute("sql_exception", true);
                }
            } else if (request.getParameter("delete_product_by_id") != null
                    && !request.getParameter("delete_product_by_id").equals("")) {
                try {
                    sqLrequests.deleteProductById((int)Integer.parseInt(request.getParameter("delete_product_by_id")));
                } catch (SQLException ignored) {
                    request.getSession().setAttribute("sql_exception", true);
                }
            }
            request.getRequestDispatcher("/admin-panel.jsp").forward(request, response);
        } else request.getRequestDispatcher("/authorization.jsp").forward(request, response);
    }
}
