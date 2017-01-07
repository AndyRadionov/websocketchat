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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import static com.aradionov.socketchat.util.ServletMessageProcessor.writeResponseMessage;

/**
 * @author Andrey Radionov
 */
public class LoginServlet extends HttpServlet {
    public static final String PATH = "/api/login";
    private final DBManager dbManager;
    private final Map<String, String> onlineUsers;

    public LoginServlet(DBManager dbManager, Map<String, String> onlineUsers) {
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

        User user = userDao.getUserByLogin(login);
        if (user == null) {
            writeResponseMessage(resp, HttpServletResponse.SC_BAD_REQUEST, "User not found!");
            return;
        }

        try {
            byte[] salt = PassEncryptTool.fromHex(user.getSalt());
            String encryptedPass = PassEncryptTool.generateStrongPasswordHash(password, salt);

            if (user.getPassword().equals(encryptedPass)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                onlineUsers.put(req.getSession().getId(), login);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            writeResponseMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error");
        }

        session.close();
    }
}
