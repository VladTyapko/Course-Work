package com.shoprich.ShopRich.controller;

import com.shoprich.ShopRich.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "choose-product-id", value = "/choose-product-id")
public class ChooseProductIdServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (User.getAdminsEmails().contains((String) request.getSession().getAttribute("isAuthorized"))) {
            request.getSession().setAttribute("product_to_choose_id", (int) Integer.parseInt(request.getParameter("product_id")));
            request.getSession().setAttribute("scroll_to_bottom",true);
            request.getRequestDispatcher("/admin-panel.jsp").forward(request,response);
        } else request.getRequestDispatcher("/authorization.jsp").forward(request, response);
    }
}
