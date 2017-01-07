package com.aradionov.socketchat.servlets;

import com.aradionov.socketchat.dao.DBManager;
import com.aradionov.socketchat.dao.UserDao;
import com.aradionov.socketchat.model.User;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.aradionov.socketchat.util.ServletMessageProcessor.writeResponseMessage;

/**
 * @author Andrey Radionov
 */
public class LogoutServlet extends HttpServlet {
    public static final String PATH = "/api/logout";
    private final Map<String, String> onlineUsers;

    public LogoutServlet(Map<String, String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = onlineUsers.remove(req.getSession().getId());

        if (login == null) {
            writeResponseMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "User not found!");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
