package com.aradionov.socketchat.chat;

import com.aradionov.socketchat.dao.DBManager;
import org.eclipse.jetty.websocket.servlet.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author Andrey Radionov
 */
public class WebSocketChatServlet extends WebSocketServlet {
    public static final String PATH = "/api/chat";
    private final static int LOGOUT_TIME = 10 * 60 * 1000;
    private final DBManager dbManager;
    private final ChatService chatService;
    private final Map<String, String> onlineUsers;

    public WebSocketChatServlet(DBManager dbManager, Map<String, String> onlineUsers) {
        this.dbManager = dbManager;
        this.chatService = new ChatService();
        this.onlineUsers = onlineUsers;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.setCreator(new SocketCreator());
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
    }

    private class SocketCreator implements WebSocketCreator {

        @Override
        public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
            HttpSession httpSession = req.getSession();
            if (onlineUsers.containsKey(httpSession.getId())) {
                return new ChatWebSocket(chatService, dbManager.getSession(), onlineUsers.get(httpSession.getId()));
            }
            try {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
