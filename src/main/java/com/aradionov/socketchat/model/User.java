package com.aradionov.socketchat.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Andrey Radionov
 */

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "LOGIN", columnDefinition = "VARCHAR", length = 15, unique = true)
    private String login;

    @Column(name = "REGISTER_DATE", columnDefinition = "TIMESTAMP")
    private Date registerDate;

    @Column(name = "PASSWORD", columnDefinition = "VARCHAR")
    private String password;

    @Column(name = "SALT", columnDefinition = "VARCHAR")
    private String salt;

    @Column(name = "ROLE", columnDefinition = "VARCHAR")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {
    }

    public User(String login, String password, String salt, UserRole role) {
        this.login = login;
        this.registerDate = new Date();
        this.password = password;
        this.salt = salt;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
