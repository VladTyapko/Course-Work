package com.shoprich.ShopRich.controller;

import com.shoprich.ShopRich.database.SQLrequests;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@WebServlet(name = "buy-product", value = "/buy-product")
public class ProductsBuyServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getSession().getAttribute("isAuthorized") != null) {
            SQLrequests sqLrequests = new SQLrequests();
            Date now = new Date();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            String purDateAndTime = dateFormat.format(now);
            int productId = Integer.parseInt(request.getParameter("product_id"));
            int purQuantity = Integer.parseInt(request.getParameter("pur_quantity"));
            int newProductQuantity = Integer.parseInt(request.getParameter("product_quantity")) - purQuantity;
            String userEmail = (String) request.getSession().getAttribute("isAuthorized");

            try {
                boolean result = sqLrequests.buyProductForUser(userEmail, productId, purQuantity, newProductQuantity, purDateAndTime);
                if (result) {
                    request.getSession().setAttribute("successful_buy", true);
                } else request.getSession().setAttribute("black_list",true);
            } catch (SQLException throwables) {
                request.getSession().setAttribute("sql_exception", true);
                forwarderToAuth(request, response, false);
            }
            forwarderToAuth(request, response, false);
        } else {
            forwarderToAuth(request, response, true);
        }
    }

    public void forwarderToAuth(HttpServletRequest request, HttpServletResponse response, boolean toAuthOrToSessions) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher;
        if (toAuthOrToSessions) {
            requestDispatcher = servletContext.getRequestDispatcher("/authorization.jsp");
        } else {
            requestDispatcher = servletContext.getRequestDispatcher("/products.jsp");
        }
        requestDispatcher.forward(request, response);
    }
}
