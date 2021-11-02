package com.epam.esm.dao.impl;

import com.epam.esm.dao.DAOException;
import com.epam.esm.dao.Dao;
import com.epam.esm.entity.Tag;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class TagDAOImpl implements Dao<Tag> {
    private static final Logger LOG = Logger.getLogger(TagDAOImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public TagDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Tag> get(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        return Optional.of(session.get(Tag.class, id));
    }

    @Override
    public List<Tag> getAll(Integer page) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<Tag> criteriaQuery = session.createQuery("from Tag", Tag.class);
        return criteriaQuery.setFirstResult(page * 10).setMaxResults(10).getResultList();
    }

    @Override
    @Transactional
    public void save(Tag tag) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(tag);
    }

    @Override
    public void update(Tag tag) {
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<?> criteriaQuery = session.createQuery("DELETE FROM Tag WHERE id = :tagId");
        criteriaQuery.setParameter("tagId", id);
        criteriaQuery.executeUpdate();
    }

    public boolean isTagExist(Integer id) throws DAOException {
        Session session = this.sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Tag.class, id)).isPresent();
    }
}
