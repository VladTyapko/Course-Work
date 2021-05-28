package com.shoprich.ShopRich.controller;

import com.shoprich.ShopRich.database.SQLrequests;
import com.shoprich.ShopRich.model.EmailSender;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "remind-password", value = "/remind-password")
public class RemindPasswordServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("email_to_send_password") != null && !request.getParameter("email_to_send_password").equals("")) {
            SQLrequests sqLrequests = new SQLrequests();
            String email = request.getParameter("email_to_send_password");
            String password = "none";
            try {
                password = sqLrequests.getUserPasswordByEmail(email);
            } catch (SQLException throwables) {
                request.getSession().setAttribute("sql_exception", true);
                forwarderToAuth(request, response, false);
                return;
            }
            if (password.equals("none")) {
                request.getSession().setAttribute("no_user", true);
                forwarderToAuth(request, response, false);
                return;
            } else {
                long remindedTime = 0;
                try {
                    remindedTime = Long.parseLong(sqLrequests.getUserRemindTimeByEmail(email));
                    sqLrequests.changeRemindTime(email, String.valueOf(System.currentTimeMillis()));
                } catch (SQLException ignored) {
                    request.getSession().setAttribute("sql_exception", true);
                    forwarderToAuth(request, response, false);
                    return;
                }
                if ((System.currentTimeMillis() - remindedTime) > 0.6 * 1_000_000) {// 10 минут
                    String textToSend = "Your password is " + password + " . Please change your old password in menu ->" +
                            " 'My account' to not forget it one more time.";
                    EmailSender emailSender = new EmailSender(email, textToSend);
                    Thread send = new Thread(emailSender);
                    send.start();
                    request.getSession().setAttribute("successful_remind", true);
                } else request.getSession().setAttribute("wait_for_remind", true);
            }
        }
        forwarderToAuth(request, response, true);
    }

    public void forwarderToAuth(HttpServletRequest request, HttpServletResponse response, boolean toAuthOrToRemindPassword) throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher;
        if (toAuthOrToRemindPassword) {
            requestDispatcher = servletContext.getRequestDispatcher("/authorization.jsp");
        } else {
            requestDispatcher = servletContext.getRequestDispatcher("/remind-password.jsp");
        }
        requestDispatcher.forward(request, response);
    }
}
