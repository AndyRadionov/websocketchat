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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(name = "TEXT", columnDefinition = "VARCHAR")
    private String text;

    @Column(name = "SEND_DATE", columnDefinition = "TIMESTAMP")
    private Date sendDate;

    public Message() {
    }

    public Message(User sender, String text) {
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
