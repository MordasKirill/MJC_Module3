package com.epam.esm.dao.impl;

import com.epam.esm.dao.DAOException;
import com.epam.esm.dao.Dao;
import com.epam.esm.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsersDAOImpl implements Dao<User> {
    private static final Logger LOG = Logger.getLogger(UsersDAOImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public UsersDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAll(Integer page) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<User> criteriaQuery = session.createQuery("from User ", User.class);
        return criteriaQuery.setFirstResult(page * 10).setMaxResults(10).getResultList();
    }

    @Override
    public Optional<User> get(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        return Optional.of(session.get(User.class, id));
    }

    public boolean isUserExist(Integer id) throws DAOException {
        Session session = this.sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(User.class, id)).isPresent();
    }

    @Override
    public void save(User user) {
    }

    @Override
    public void update(User user) {
    }

    @Override
    public void delete(Integer id) {
    }
}
