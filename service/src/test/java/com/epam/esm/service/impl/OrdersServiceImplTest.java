package com.epam.esm.service.impl;

import com.epam.esm.dao.DAOException;
import com.epam.esm.dao.impl.CertificatesDAOImpl;
import com.epam.esm.dao.impl.OrderDAOImpl;
import com.epam.esm.dao.impl.UsersDAOImpl;
import com.epam.esm.entity.Order;
import com.epam.esm.service.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class OrdersServiceImplTest {
    private static final Order order = new Order();
    @Mock
    private OrderDAOImpl orderDAO;
    @Mock
    private UsersDAOImpl usersDAO;
    @Mock
    private CertificatesDAOImpl certificatesDAO;
    @InjectMocks
    private CertificateServiceImpl certificateService;
    @InjectMocks
    private OrdersServiceImpl service;
    @InjectMocks
    private UsersServiceImpl usersService;

    @Test
    void createOrderExc() throws ServiceException {
        order.setId(5);
        Mockito.when(certificateService.isCertificateExist(order.getCertificateId())).thenReturn(false);
        assertThrows(ServiceException.class, () -> service.createOrder(order), "Fail");
    }

    @Test
    void createOrder() throws ServiceException {
        order.setCertificateId(1);
        order.setPrice(11.2);
        Mockito.when(certificateService.isCertificateExist(order.getCertificateId())).thenReturn(true);
        service.createOrder(order);
        Mockito.verify(orderDAO).save(order);
    }

    @Test
    void getOrders() throws ServiceException {
        service.getOrders(1);
        Mockito.verify(orderDAO).getAll(1);
    }

    @Test
    void getOrder() throws ServiceException {
        Mockito.when(service.isOrderExist(1)).thenReturn(true);
        service.getOrder(1);
        Mockito.verify(orderDAO).get(1);
    }

    @Test
    void getOrderNotExc() throws DAOException {
        Mockito.doThrow(new DAOException()).when(orderDAO).isOrderExist(5);
        assertThrows(ServiceException.class, () -> service.getOrder(5), "UpdateCertificate fail");
    }

    @Test
    void getOrderNotExist() throws ServiceException {
        Mockito.when(service.isOrderExist(1)).thenReturn(false);
        assertThrows(ServiceException.class, () -> service.getOrder(1), "UpdateCertificate fail");
    }

    @Test
    void isOrderExist() throws ServiceException, DAOException {
        service.isOrderExist(1);
        Mockito.verify(orderDAO).isOrderExist(1);
    }

    @Test
    void isOrderExistExc() throws ServiceException, DAOException {
        Mockito.doThrow(new DAOException()).when(orderDAO).isOrderExist(5);
        assertThrows(ServiceException.class, () -> service.isOrderExist(5), "UpdateCertificate fail");
    }

    @Test
    void getOrderByUserIdOrderId() throws ServiceException, DAOException {
        Mockito.when(usersService.isUserExist(1)).thenReturn(true);
        Mockito.when(service.isOrderExist(1)).thenReturn(true);
        service.getOrderByUserIdOrderId(1, 1);
        Mockito.verify(orderDAO).getOrderByUserIdOrderId(1, 1);
    }

    @Test
    void getMaxAndPriceMostFrequentTagsExc() throws ServiceException {
        Mockito.when(usersService.isUserExist(1)).thenReturn(false);
        assertThrows(ServiceException.class, () -> service.getMaxAndPriceMostFrequentTags(1), "UpdateCertificate fail");
    }

    @Test
    void getMaxAndPriceMostFrequentTags() throws ServiceException {
        Mockito.when(usersService.isUserExist(1)).thenReturn(true);
        service.getMaxAndPriceMostFrequentTags(1);
        Mockito.verify(orderDAO).getMaxAndPriceMostFrequentTags(1);
    }

    @Test
    void getOrderByUserIdOrderIdExc() {
        assertThrows(ServiceException.class, () -> service.getOrderByUserIdOrderId(null, null), "UpdateCertificate fail");
    }
}