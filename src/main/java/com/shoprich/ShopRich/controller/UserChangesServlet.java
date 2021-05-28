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

@WebServlet(name = "user-change", value = "/user-change")
public class UserChangesServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getSession().getAttribute("isAuthorized") != null) {
            String newUserName = request.getParameter("user_new_name");
            String newUserPhone = request.getParameter("user_new_phone");
            String newUserPassword = request.getParameter("user_new_password");
            int userId = Integer.parseInt(request.getParameter("user_id"));
            SQLrequests sqLrequests = new SQLrequests();
            try {
                sqLrequests.editUserInfo(newUserName, newUserPassword, userId, newUserPhone);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
           request.getRequestDispatcher("/my-account.jsp").forward(request,response);
        } else {
          request.getRequestDispatcher("/authorization.jsp").forward(request,response);
        }

    }
}
