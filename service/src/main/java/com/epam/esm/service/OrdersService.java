package com.epam.esm.service;

import com.epam.esm.entity.Order;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrdersService {
    /**
     * @param order entity used to create order
     * @throws ServiceException exception in Service layer
     */
    void createOrder(Order order) throws ServiceException;

    /**
     * @return List with Order entity's
     * @throws ServiceException exception in Service layer
     */
    List<Order> getOrders(Integer page) throws ServiceException;

    /**
     * @return Single certificate with Order entity's
     * @throws ServiceException exception in Service layer
     */
    Optional<Order> getOrder(Integer id) throws ServiceException;

    /**
     * @param id id to be checked
     * @return true/false is exists
     * @throws ServiceException exception in Service layer
     */
    boolean isOrderExist(Integer id) throws ServiceException;

    /**
     * @return Get single order entity by user id and order id
     */
    Order getOrderByUserIdOrderId(Integer userId, Integer orderId) throws ServiceException;

    /**
     * @param userId to find certificates for current user
     * @return Map<String, String> where key is max price
     * and value is most frequently  used tag
     */
    Map<String, String> getMaxAndPriceMostFrequentTags(int userId) throws ServiceException;
}
