package com.epam.esm.service.impl;

import com.epam.esm.dao.DAOException;
import com.epam.esm.dao.impl.CertificatesDAOImpl;
import com.epam.esm.dao.impl.OrderDAOImpl;
import com.epam.esm.dao.impl.UsersDAOImpl;
import com.epam.esm.entity.Order;
import com.epam.esm.service.OrdersService;
import com.epam.esm.service.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrdersServiceImpl implements OrdersService {
    private final OrderDAOImpl orderDAOImpl;
    private final CertificatesDAOImpl certificatesDAO;
    private final UsersDAOImpl usersDAO;

    public OrdersServiceImpl(OrderDAOImpl orderDAOImpl, CertificatesDAOImpl certificatesDAO, UsersDAOImpl usersDAO) {
        this.usersDAO = usersDAO;
        this.orderDAOImpl = orderDAOImpl;
        this.certificatesDAO = certificatesDAO;
    }

    @Override
    public void createOrder(Order order) throws ServiceException {
        try {
            if (!certificatesDAO.isCertificateExist(order.getCertificateId())) {
                throw new ServiceException("Cant find certificate with id: " + order.getCertificateId());
            }
            orderDAOImpl.save(order);
        } catch (DAOException e) {
            throw new ServiceException("CreateOrder fail.");
        }
    }

    @Override
    public List<Order> getOrders(Integer page) throws ServiceException {
        return orderDAOImpl.getAll(page);
    }

    @Override
    public Optional<Order> getOrder(Integer id) throws ServiceException {
        try {
            if (!orderDAOImpl.isOrderExist(id)) {
                throw new ServiceException("Cant find user by order id: " + id);
            }
            return orderDAOImpl.get(id);
        } catch (DAOException e) {
            throw new ServiceException("Get user fail!");
        }
    }

    @Override
    public boolean isOrderExist(Integer id) throws ServiceException {
        try {
            return orderDAOImpl.isOrderExist(id);
        } catch (DAOException e) {
            throw new ServiceException("Check certificate fail", e);
        }
    }

    @Override
    public Order getOrderByUserIdOrderId(Integer userId, Integer orderId) throws ServiceException {
        try {
            if (!usersDAO.isUserExist(userId) || !orderDAOImpl.isOrderExist(orderId)) {
                throw new ServiceException("Cant find user: " + userId + " or order with id: " + orderId);
            }
            return orderDAOImpl.getOrderByUserIdOrderId(userId, orderId);
        } catch (DAOException e) {
            throw new ServiceException("Get user fail!");
        }
    }

    public Map<String, String> getMaxAndPriceMostFrequentTags(int userId) throws ServiceException {
        try {
            if (!usersDAO.isUserExist(userId)) {
                throw new ServiceException("Cant find user with id: " + userId);
            }
            return orderDAOImpl.getMaxAndPriceMostFrequentTags(userId);
        } catch (DAOException e) {
            throw new ServiceException("Get getMaxAndPriceMostFrequentTags fail!");
        }
    }
}
