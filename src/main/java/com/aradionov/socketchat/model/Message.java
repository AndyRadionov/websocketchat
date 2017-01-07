package com.aradionov.socketchat.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Andrey Radionov
 */

@Entity
@Table(name = "messages")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "sender", columnDefinition = "VARCHAR", nullable = false)
    private String sender;

    @Column(name = "TEXT", columnDefinition = "VARCHAR")
    private String text;

    @Column(name = "SEND_DATE", columnDefinition = "TIMESTAMP")
    private Date sendDate;

    public Message() {
    }

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
        this.sendDate = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (sender != null ? !sender.equals(message.sender) : message.sender != null) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        return sendDate != null ? sendDate.equals(message.sendDate) : message.sendDate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (sendDate != null ? sendDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return sender + ": " + text;
    }
}
