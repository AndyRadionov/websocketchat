package com.aradionov.socketchat.main;

import com.aradionov.socketchat.chat.WebSocketChatServlet;
import com.aradionov.socketchat.dao.DBManager;
import com.aradionov.socketchat.servlets.LoginServlet;
import com.aradionov.socketchat.servlets.LogoutServlet;
import com.aradionov.socketchat.servlets.RegisterServlet;
import com.aradionov.socketchat.servlets.UsersServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Andrey Radionov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        final DBManager dbManager = new DBManager();
        final Map<String, String> onlineUsers = new ConcurrentHashMap<>();

        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletHandler.addServlet(new ServletHolder(new RegisterServlet(dbManager, onlineUsers)), RegisterServlet.PATH);
        servletHandler.addServlet(new ServletHolder(new LoginServlet(dbManager, onlineUsers)), LoginServlet.PATH);
        servletHandler.addServlet(new ServletHolder(new LogoutServlet(onlineUsers)), LogoutServlet.PATH);
        servletHandler.addServlet(new ServletHolder(new WebSocketChatServlet(onlineUsers)), WebSocketChatServlet.PATH);
        servletHandler.addServlet(new ServletHolder(new UsersServlet(onlineUsers)), UsersServlet.PATH);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, servletHandler});

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
