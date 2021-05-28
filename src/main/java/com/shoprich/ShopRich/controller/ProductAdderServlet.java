package com.shoprich.ShopRich.controller;

import com.shoprich.ShopRich.database.SQLrequests;
//import com.shoprich.ShopRich.model.Seance;
import com.shoprich.ShopRich.model.Product;
import com.shoprich.ShopRich.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "add-product", value = "/add-product")
public class ProductAdderServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (User.getAdminsEmails().contains((String) request.getSession().getAttribute("isAuthorized"))) {
            String productImgUrl = "none";
            if (request.getParameter("product_img_url") != null && !request.getParameter("product_img_url").equals("")) {
                productImgUrl = request.getParameter("product_img_url");
            }
            Product product = new Product(Integer.parseInt(request.getParameter("product_quantity")),
                    request.getParameter("product_name"), request.getParameter("product_desc"),
                    productImgUrl, Integer.parseInt(request.getParameter("product_price")));
            SQLrequests sqLrequests = new SQLrequests();
            try {
                sqLrequests.addNewProduct(product);
            } catch (SQLException ignored) {
                request.getSession().setAttribute("sql_exception", true);
            }
            request.getRequestDispatcher("/admin-panel.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/products.jsp").forward(request, response);
        }
    }

}
