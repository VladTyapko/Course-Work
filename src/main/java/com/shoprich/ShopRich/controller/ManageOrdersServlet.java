package com.shoprich.ShopRich.controller;

import com.shoprich.ShopRich.database.SQLrequests;
import com.shoprich.ShopRich.model.EmailSender;
import com.shoprich.ShopRich.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

@WebServlet(name = "manage-order", value = "/manage-order")
public class ManageOrdersServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (User.getManagersEmails().contains((String) request.getSession().getAttribute("isAuthorized"))) {
            SQLrequests sqLrequests = new SQLrequests();
            if (request.getParameter("cancel_order") != null) {
                try {
                    sqLrequests.clearOnePurHistory(Integer.parseInt(request.getParameter("purchase_id")), request.getParameter("user_email"));
                    sqLrequests.deletePurProcess(Integer.parseInt(request.getParameter("pur_process_id")));
                } catch (SQLException throwables) {
                    request.getRequestDispatcher("/all-purchases.jsp").forward(request, response);
                    return;
                }
            } else if (request.getParameter("submit_order") != null) {
                try {
                    sqLrequests.setOrderStatusReady(Integer.parseInt(request.getParameter("pur_process_id")));
                } catch (SQLException throwables) {
                    request.getRequestDispatcher("/all-purchases.jsp").forward(request, response);
                    return;
                }
                long randomCode = Math.abs(new Random().nextLong());
                String emailText = "Your order is checked, pay " + request.getParameter("price") + " to 'MasterCard': 2000 0000 2000 8931 " +
                        "with comment: "+randomCode+" . Then we will send you product!)";
                EmailSender emailSender = new EmailSender(request.getParameter("pur_buyer_email"), emailText);
                Thread send = new Thread(emailSender);
                send.start();
            }else if (request.getParameter("finish_order")!=null){
                try {
                    sqLrequests.deleteFromPursAndOrders(Integer.parseInt(request.getParameter("pur_process_id")),Integer.parseInt(request.getParameter("purchase_id")));
                } catch (SQLException throwables) {
                    request.getRequestDispatcher("/all-purchases.jsp").forward(request, response);
                }
            }
            request.getRequestDispatcher("/all-purchases.jsp").forward(request, response);
        } else request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
}