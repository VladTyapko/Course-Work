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


@WebServlet(name = "black-list", value = "/black-list")
public class BlackListServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (User.getAdminsEmails().contains((String) request.getSession().getAttribute("isAuthorized"))) {
            SQLrequests sqLrequests = new SQLrequests();
            String userEmail;
            if (request.getParameter("user_email_to_bl") != null && !request.getParameter("user_email_to_bl").equals("")) {
                userEmail = request.getParameter("user_email_to_bl");
                try {
                    sqLrequests.userToBlackList(userEmail, true);
                } catch (SQLException ignored) {
                    request.getSession().setAttribute("sql_exception", true);
                }
            }
            if (request.getParameter("user_email_rm_bl") != null && !request.getParameter("user_email_rm_bl").equals("")) {
                userEmail = request.getParameter("user_email_rm_bl");
                try {
                    sqLrequests.userToBlackList(userEmail, false);
                } catch (SQLException ignored) {
                    request.getSession().setAttribute("sql_exception", true);
                }
            }
            request.getRequestDispatcher("/admin-panel.jsp").forward(request, response);
        } else request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
}