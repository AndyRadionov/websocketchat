package com.aradionov.socketchat.servlets;

import com.aradionov.socketchat.dao.DBManager;
import com.aradionov.socketchat.dao.UserDao;
import com.aradionov.socketchat.model.User;
import com.aradionov.socketchat.util.PassEncryptTool;
import com.aradionov.socketchat.util.ServletMessageProcessor;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.aradionov.socketchat.util.ServletMessageProcessor.*;

/**
 * @author Andrey Radionov
 */
public class LoginServlet extends HttpServlet {
    public static final String PATH = "/api/login";
    private DBManager dbManager;

    public LoginServlet(DBManager dbManager) {
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

        if (userDao.getUserByLogin(login) == null) {
            writeErrorMessage(resp, HttpServletResponse.SC_NOT_ACCEPTABLE, "User not found!");
            return;
        }

        try {
            User user = userDao.getUserByLogin(login);
            byte[] salt = PassEncryptTool.fromHex(user.getSalt());
            String encryptedPass = PassEncryptTool.generateStrongPasswordHash(password, salt);

            if (user.getPassword().equals(encryptedPass)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            writeErrorMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error");
        }

        session.close();
    }
}
