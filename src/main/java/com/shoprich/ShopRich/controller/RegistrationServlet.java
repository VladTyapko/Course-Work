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
import java.util.Random;

@WebServlet(name = "registration", value = "/registration")
public class RegistrationServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User newUser = new User(request.getParameter("reg-name"), request.getParameter("reg-email"),
                request.getParameter("reg-pass"), request.getParameter("reg-phone"));
        SQLrequests sqLrequests = new SQLrequests();
        boolean isAlreadyExist = false;
        try {
            isAlreadyExist = sqLrequests.addUser(newUser);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher;
        String newUserEmail = request.getParameter("reg-email");
        if (isAlreadyExist) {
            request.getSession().setAttribute("isAuthorized", newUserEmail);
            request.getSession().setAttribute("promocode", true);
            if (User.getAdminsEmails().contains(newUserEmail)) {
                request.getSession().setAttribute("isAdmin", true);
            }
            if (User.getManagersEmails().contains(newUserEmail)) {
                request.getSession().setAttribute("isManager", true);
            }
            requestDispatcher = servletContext.getRequestDispatcher("/products.jsp");
        } else {
            request.getSession().setAttribute("tryOtherEmail", true);
            requestDispatcher = servletContext.getRequestDispatcher("/authorization.jsp");
        }
        requestDispatcher.forward(request, response);
    }
}
