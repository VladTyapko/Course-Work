package com.shoprich.ShopRich.controller;

import com.shoprich.ShopRich.database.SQLrequests;
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

@WebServlet(name = "authorization", value = "/authorization")
public class AuthorizationServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User oldUser = new User(request.getParameter("user-email"), request.getParameter("user-password"));
        SQLrequests sqLrequests = new SQLrequests();
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher;
        try {
            if (sqLrequests.userAuthorization(oldUser, request)) {
                if (User.getAdminsEmails().contains(oldUser.getEmail())) {
                    request.getSession().setAttribute("isAdmin", true);
                }
                if (User.getManagersEmails().contains(oldUser.getEmail())){
                    request.getSession().setAttribute("isManager", true);
                }
                request.getSession().setAttribute("isAuthorized", request.getParameter("user-email"));
                requestDispatcher = servletContext.getRequestDispatcher("/products.jsp");
            } else {
                requestDispatcher = servletContext.getRequestDispatcher("/authorization.jsp");
            }
            requestDispatcher.forward(request, response);
            return;
        } catch (SQLException | ServletException throwables) {
            requestDispatcher = servletContext.getRequestDispatcher("/authorization.jsp");
        }
        requestDispatcher.forward(request, response);
    }
}