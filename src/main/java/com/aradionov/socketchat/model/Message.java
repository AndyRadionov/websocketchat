package com.aradionov.socketchat.model;

import java.util.Date;

/**
 * @author Andrey Radionov
 */
public class Message {
    private User sender;
    private String text;
    private Date sendDate;

    public Message() {
    }

    public Message(User sender, String text) {
        this.sender = sender;
        this.text = text;
        this.sendDate = new Date();
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
