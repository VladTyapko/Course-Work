package com.shoprich.ShopRich.controller;

import com.shoprich.ShopRich.database.SQLrequests;
import com.shoprich.ShopRich.model.User;
import org.postgresql.core.SqlCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "delete-all-purs", value = "/delete-all-purs")
public class DeleteAllPurchasesServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (User.getAdminsEmails().contains((String) request.getSession().getAttribute("isAuthorized"))) {
            if (request.getParameter("delete_all_purs") != null && request.getParameter("delete_all_purs").equals("Delete all purchases")) {
                SQLrequests sqLrequests = new SQLrequests();
                try {
                    sqLrequests.deleteAllPurchases();
                } catch (SQLException ignored) {
                    request.getSession().setAttribute("sql_exception", true);
                    request.getRequestDispatcher("/admin-panel.jsp").forward(request, response);
                    return;
                }
            }
            request.getRequestDispatcher("/admin-panel.jsp").forward(request, response);
        } else request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
}