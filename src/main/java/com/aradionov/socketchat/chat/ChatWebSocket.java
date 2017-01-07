package com.aradionov.socketchat.chat;

import com.aradionov.socketchat.dao.MessageDao;
import com.aradionov.socketchat.model.Message;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.Date;
import java.util.List;

/**
 * @author Andrey Radionov
 */
@WebSocket
public class ChatWebSocket {
    private ChatService chatService;
    private Session session;
    private org.hibernate.Session hibernateSession;
    private MessageDao messageDao;
    private String sender;

    public ChatWebSocket(ChatService chatService, org.hibernate.Session hibernateSession, String sender) {
        this.chatService = chatService;
        this.hibernateSession = hibernateSession;
        this.sender = sender;
        this.messageDao = new MessageDao(hibernateSession);
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        chatService.add(this);
        this.session = session;
        for (Message message : messageDao.getAllMessages()) {
            this.sendString(message.getSender() + ": " + message.getText());
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        Message message = new Message(sender, data);
        message.setSendDate(new Date());
        messageDao.insertMessage(message);
        chatService.sendMessage(sender + ": " + data);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        hibernateSession.close();
        chatService.remove(this);
    }

    public void sendString(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
