package com.shoprich.ShopRich.controller;

import com.shoprich.ShopRich.database.SQLrequests;
import com.shoprich.ShopRich.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "make-order", value = "/make-order")
public class MakeOrderServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getSession().getAttribute("isAuthorized") != null && request.getParameter("pur_id") != null) {
            int purPrice = Integer.parseInt(request.getParameter("pur_price"));
            if (request.getParameter("promocode") != null && request.getParameter("promocode").equals("promocode")) {
                purPrice = (int) (purPrice * 0.85);
            }
            Order order = new Order(Integer.parseInt(request.getParameter("pur_id")), request.getParameter("fName"),
                    request.getParameter("sName"), request.getParameter("email"), request.getParameter("phone"),
                    request.getParameter("address"), purPrice);
            SQLrequests sqLrequests = new SQLrequests();
            try {
                sqLrequests.addNewOrderProc(order);
            } catch (SQLException ignored) {
                request.getSession().setAttribute("sql_exception", true);
                request.getRequestDispatcher("/my-goods.jsp").forward(request, response);
                return;
            }
            request.getSession().setAttribute("order_in_proc",true);
            request.getRequestDispatcher("/my-goods.jsp").forward(request, response);
        } else request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
}