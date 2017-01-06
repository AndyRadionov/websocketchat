package com.aradionov.socketchat.dao;

import com.aradionov.socketchat.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author Andrey Radionov
 */
public class UserDao {
    private Session session;

    public UserDao(Session session) {
        this.session = session;
    }

    public User getUserById(long id) {
        return (User) session.get(User.class, id);
    }

    public User getUserByLogin(String login) {
        Criteria criteria = session.createCriteria(User.class);
        return (User) criteria.add(Restrictions.eq("login", login)).uniqueResult();
    }

    public List<User> getAllUsers() {
        return session.createCriteria(User.class).list();
    }

    public long inserUser(User user) {
        return (long) session.save(user);
    }
}
