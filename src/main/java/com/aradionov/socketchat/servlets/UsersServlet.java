package com.aradionov.socketchat.servlets;

import com.aradionov.socketchat.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Andrey Radionov
 */
public class UsersServlet extends HttpServlet {
    public static final String PATH = "/api/users";
    private final Map<String, String> onlineUsers;

    public UsersServlet(Map<String, String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (onlineUsers.size() > 0) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(onlineUsers.values(), new TypeToken<List<User>>() {}.getType());

            resp.getWriter().write(element.toString());
        } else {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
