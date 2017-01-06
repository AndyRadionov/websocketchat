package com.aradionov.socketchat.model;

import java.util.Date;

/**
 * @author Andrey Radionov
 */
public class User {
    private String login;
    private Date registerDate;
    private String password;
    private String salt;
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
