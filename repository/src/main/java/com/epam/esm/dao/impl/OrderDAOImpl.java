package com.epam.esm.dao.impl;

import com.epam.esm.dao.DAOException;
import com.epam.esm.dao.Dao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class OrderDAOImpl implements Dao<Order> {
    private final SessionFactory sessionFactory;

    @Autowired
    public OrderDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Order> get(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        return Optional.of(session.get(Order.class, id));
    }

    @Override
    public List<Order> getAll(Integer page) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<Order> criteriaQuery = session.createQuery("from Order ", Order.class);
        return criteriaQuery.setFirstResult(page * 10).setMaxResults(10).getResultList();
    }

    public Order getOrderByUserIdOrderId(Integer userId, Integer orderId) throws DAOException {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Order> criteriaQuery = session.createQuery("from Order orders join FETCH orders.users users where users.id = :userId and orders.id = :orderId", Order.class);
        criteriaQuery.setParameter("userId", userId);
        criteriaQuery.setParameter("orderId", orderId);
        if (criteriaQuery.getResultList().size() == 0) {
            throw new DAOException("Cant find order with id: " + orderId + " with user id: " + userId);
        }
        return criteriaQuery.getResultList().get(0);
    }

    @Override
    @Transactional
    public void save(Order order) {
        Session session = this.sessionFactory.getCurrentSession();
        order.setPrice(session.get(Certificate.class, order.getCertificateId()).getPrice());
        //Query<?> queryForOrder = session.createSQLQuery("insert into users_has_orders (users_id, orders_id) values (?,?)");
        //queryForOrder.setParameter(1, order.getUsers().ge)
        session.persist(order);
    }

    public Map<String, String> getMaxAndPriceMostFrequentTags(int userId) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Integer> tagList = new ArrayList<>();
        TypedQuery<Order> queryForOrder = session.createQuery("from Order orders join FETCH orders.users users where users.id = :userId", Order.class);
        queryForOrder.setParameter("userId", userId);
        for (Order order : queryForOrder.getResultList()) {
            TypedQuery<Tag> tagTypedQuery = session.createQuery("from Tag tag join fetch tag.certificates cert where cert.id = :cerfId", Tag.class);
            tagTypedQuery.setParameter("cerfId", order.getCertificateId());
            tagList.add(tagTypedQuery.getResultList().get(0).getId());
        }
        Query<Double> getMaxPrice = session.createQuery("select max (price) from Order ", Double.class);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("Max price: " + getMaxPrice.getResultList().get(0),
                "Most frequent tag: " + getMostFrequent(tagList));
        return resultMap;
    }

    public boolean isOrderExist(Integer id) throws DAOException {
        Session session = this.sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Order.class, id)).isPresent();
    }

    private List<Integer> getMostFrequent(List<Integer> tagId) {
        Map<Integer, Integer> counts = new HashMap<>();
        List<Integer> result = new ArrayList<>();
        int highestFrequency = 0;
        for (int id : tagId) {
            int currFrequency = counts.getOrDefault(id, 0) + 1;
            counts.put(id, currFrequency);
            highestFrequency = Math.max(currFrequency, highestFrequency);
        }
        for (int key : counts.keySet()) {
            if (counts.get(key) == highestFrequency) {
                result.add(key);
            }
        }
        return result;
    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void delete(Integer id) {

    }
}
