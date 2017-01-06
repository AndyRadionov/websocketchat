package com.aradionov.socketchat.dao;

import com.aradionov.socketchat.model.Message;
import org.hibernate.Session;

import java.util.List;

/**
 * @author Andrey Radionov
 */
public class MessageDao {
    private Session session;

    public MessageDao(Session session) {
        this.session = session;
    }

    public Message getUserById(long id) {
        return (Message) session.get(Message.class, id);
    }

    public List<Message> getAllMessages() {
        return session.createCriteria(Message.class).list();
    }

    public long inserMessage(Message message) {
        return (long) session.save(message);
    }
}
