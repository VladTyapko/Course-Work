package com.shoprich.ShopRich.controller;

import com.shoprich.ShopRich.database.SQLrequests;
//import com.shoprich.ShopRich.model.Seance;
import com.shoprich.ShopRich.database.SqlRequestsInterface;
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

@WebServlet(name = "edit-product", value = "/edit-product")
public class ProductsEditorServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (User.getAdminsEmails().contains((String) request.getSession().getAttribute("isAuthorized"))) {
            if (request.getParameter("product_id") != null && !request.getParameter("product_id").equals("")) {
                SQLrequests sqLrequests = new SQLrequests();
                Product product = new Product(Integer.parseInt(request.getParameter("product_quantity")),
                        request.getParameter("product_name"), request.getParameter("product_desc"),
                        request.getParameter("product_img_url"), Integer.parseInt(request.getParameter("product_price")));
                product.setProductId(Integer.parseInt(request.getParameter("product_id")));
                try {
                    sqLrequests.editProductById(product);
                } catch (SQLException ignored) {
                    request.getRequestDispatcher("/admin-panel.jsp").forward(request, response);
                    return;
                }
            }
            request.getRequestDispatcher("/admin-panel.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/products.jsp").forward(request, response);
        }
    }

}
