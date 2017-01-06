package com.aradionov.socketchat.servlets;

import com.aradionov.socketchat.dao.DBManager;
import com.aradionov.socketchat.dao.UserDao;
import com.aradionov.socketchat.model.User;
import com.aradionov.socketchat.model.UserRole;
import com.aradionov.socketchat.util.PassEncryptUtil;
import com.sun.deploy.net.HttpResponse;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;

/**
 * @author Andrey Radionov
 */
public class RegisterServlet extends HttpServlet {
    public static final String PATH = "/api/signup";
    private DBManager dbManager;

    public RegisterServlet(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login.isEmpty() || password.isEmpty()) {
            writeErrorMessage(resp, HttpServletResponse.SC_NOT_ACCEPTABLE, "Wrong Login/Password!");
            return;
        }

        Session session = dbManager.getSession();
        UserDao userDao = new UserDao(session);

        if (userDao.getUserByLogin(login) != null) {
            writeErrorMessage(resp, HttpServletResponse.SC_NOT_ACCEPTABLE, "Login already in use!");
            return;
        }

        try {
            String salt = PassEncryptUtil.getSalt();
            String encryptedPass = PassEncryptUtil.generateStrongPasswordHash(password, salt);
            userDao.inserUser(new User(login, encryptedPass, salt, UserRole.USER));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            writeErrorMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error");
        }

        session.close();
    }

    private void writeErrorMessage(HttpServletResponse resp, int status, String errorMessage) throws IOException {
        resp.setStatus(status);
        String json = String.format(Locale.ROOT, "{Error: %s}", errorMessage);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
