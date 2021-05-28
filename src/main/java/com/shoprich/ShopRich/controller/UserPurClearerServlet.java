package com.shoprich.ShopRich.controller;

import com.shoprich.ShopRich.database.SQLrequests;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "clear-purchases", value = "/clear-purchases")
public class UserPurClearerServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("good_pur_id") != null && !request.getParameter("good_pur_id").equals("")) {
            int purId = Integer.parseInt(request.getParameter("good_pur_id"));
            SQLrequests sqLrequests = new SQLrequests();
            String res;
            try {
                res = sqLrequests.getPurOrderStatus(purId);
            } catch (SQLException ignored) {
                res = "none";
            }
            if (res.equals("ready")) {
                request.setAttribute("order_not_available", true);
                request.getRequestDispatcher("/my-goods.jsp").forward(request, response);
                return;
            } else  {
                try {
                    sqLrequests.deleteNotReadyPur(purId);
                } catch (SQLException ignored) {
                }
            }
            if (request.getParameter("good_id_to_delete") != null && !request.getParameter("good_id_to_delete").equals("")) {
                try {
                    sqLrequests.clearOnePurHistory(Integer.parseInt(request.getParameter("good_pur_id")),
                            (String) request.getSession().getAttribute("isAuthorized"));
                } catch (SQLException ignored) {
                    request.getSession().setAttribute("sql_exception", true);
                    request.getRequestDispatcher("/my-goods.jsp").forward(request, response);
                    return;
                }
            }
//            if (request.getParameter("clear_history") != null &&
//                    (request.getParameter("clear_history")).equals("Clear history")) {
//                try {
//                    sqLrequests.clearAllUserPurHistory((String) request.getSession().getAttribute("isAuthorized"));
//                } catch (SQLException ignored) {
//                    request.getSession().setAttribute("sql_exception", true);
//                    request.getRequestDispatcher("/my-goods.jsp").forward(request, response);
//                }
//            }
            request.getRequestDispatcher("/my-goods.jsp").forward(request, response);
        } else request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
}