package com.shoprich.ShopRich.controller;

import com.shoprich.ShopRich.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "adminForward", value = "/adminForward")
public class AdminForwarder extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher;
        if (request.getSession().getAttribute("isAuthorized") != null) {
            if (request.getParameter("chooser") != null) {
                switch (request.getParameter("chooser")) {
                    case "admin_panel"->{
                        if (User.getAdminsEmails().contains((String) request.getSession().getAttribute("isAuthorized"))) {
                            requestDispatcher = servletContext.getRequestDispatcher("/admin-panel.jsp");
                        } else {
                            requestDispatcher = servletContext.getRequestDispatcher("/products.jsp");
                        }
                       }
                    //case "main_page" -> requestDispatcher = servletContext.getRequestDispatcher("/products.jsp");
                    case "manager_pur_history" -> requestDispatcher = servletContext.getRequestDispatcher("/ready-purchases.jsp");
                    case "all_pur_history" -> requestDispatcher = servletContext.getRequestDispatcher("/all-purchases.jsp");
                    case "my_goods" -> requestDispatcher = servletContext.getRequestDispatcher("/my-goods.jsp");
                    case "my_account"-> requestDispatcher = servletContext.getRequestDispatcher("/my-account.jsp");
                    default -> requestDispatcher = servletContext.getRequestDispatcher("/products.jsp");
                }
            } else {
                requestDispatcher = servletContext.getRequestDispatcher("/products.jsp");
            }
        } else {
            requestDispatcher = servletContext.getRequestDispatcher("/authorization.jsp");
        }
        requestDispatcher.forward(request, response);
    }

}