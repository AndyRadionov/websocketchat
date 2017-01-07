package com.aradionov.socketchat.chat;

import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.Set;

/**
 * @author Andrey Radionov
 */
public class ChatService {
    private Set<ChatWebSocket> webSockets;

    public ChatService() {
        this.webSockets = new ConcurrentHashSet<>();
    }

    public void sendMessage(String data) {
        for (ChatWebSocket socket : webSockets) {
            try {
                socket.sendString(data);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void add(ChatWebSocket webSocket) {
        webSockets.add(webSocket);
    }

    public void remove(ChatWebSocket webSocket) {
        webSockets.remove(webSocket);
    }
}
