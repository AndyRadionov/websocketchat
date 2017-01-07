package com.aradionov.socketchat.servlets;

import com.aradionov.socketchat.dao.DBManager;
import com.aradionov.socketchat.dao.UserDao;
import com.aradionov.socketchat.model.User;
import com.aradionov.socketchat.model.UserRole;
import com.aradionov.socketchat.util.PassEncryptTool;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import static com.aradionov.socketchat.util.ServletMessageProcessor.*;

/**
 * @author Andrey Radionov
 */
public class RegisterServlet extends HttpServlet {
    public static final String PATH = "/api/signup";
    private final DBManager dbManager;
    private final Map<String, String> onlineUsers;

    public RegisterServlet(DBManager dbManager, Map<String, String> onlineUsers) {
        this.dbManager = dbManager;
        this.onlineUsers = onlineUsers;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login.isEmpty() || password.isEmpty()) {
            writeResponseMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "Wrong Login/Password!");
            return;
        }

        Session session = dbManager.getSession();
        UserDao userDao = new UserDao(session);

        if (userDao.getUserByLogin(login) != null) {
            writeResponseMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "Login already in use!");
            return;
        }

        try {
            byte[] salt = PassEncryptTool.getSalt();
            String encryptedPass = PassEncryptTool.generateStrongPasswordHash(password, salt);
            userDao.inserUser(new User(login, encryptedPass, PassEncryptTool.toHex(salt), UserRole.USER));

            onlineUsers.put(req.getSession().getId(), login);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            writeResponseMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error");
        }

        session.close();
    }


}
