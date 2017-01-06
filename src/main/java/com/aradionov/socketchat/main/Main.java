package com.aradionov.socketchat.main;

import com.aradionov.socketchat.dao.DBManager;
import com.aradionov.socketchat.dao.MessageDao;
import com.aradionov.socketchat.dao.UserDao;
import com.aradionov.socketchat.servlets.LoginServlet;
import com.aradionov.socketchat.servlets.LogoutServlet;
import com.aradionov.socketchat.servlets.RegisterServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author Andrey Radionov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        DBManager dbManager = new DBManager();

        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletHandler.addServlet(new ServletHolder(new RegisterServlet(dbManager)), RegisterServlet.PATH);
        servletHandler.addServlet(new ServletHolder(new LoginServlet(dbManager)), LoginServlet.PATH);
        servletHandler.addServlet(new ServletHolder(new LogoutServlet()), LogoutServlet.PATH);

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
