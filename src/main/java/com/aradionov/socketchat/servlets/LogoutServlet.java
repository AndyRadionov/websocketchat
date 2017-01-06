package com.aradionov.socketchat.servlets;

import com.aradionov.socketchat.dao.DBManager;
import com.aradionov.socketchat.dao.UserDao;
import com.aradionov.socketchat.model.User;
import com.aradionov.socketchat.util.PassEncryptTool;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.aradionov.socketchat.util.ServletMessageProcessor.writeResponseMessage;

/**
 * @author Andrey Radionov
 */
public class LogoutServlet extends HttpServlet {
    public static final String PATH = "/api/logout";
    private DBManager dbManager;

    public LogoutServlet(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");

        if (login.isEmpty()) {
            writeResponseMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "Wrong Login!");
            return;
        }

        Session session = dbManager.getSession();
        UserDao userDao = new UserDao(session);

        User user = userDao.getUserByLogin(login);
        if (user == null) {
            writeResponseMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "User not found!");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        session.close();
    }
}
