package com.epam.esm.service.impl;

import com.epam.esm.dao.DAOException;
import com.epam.esm.dao.impl.UsersDAOImpl;
import com.epam.esm.entity.User;
import com.epam.esm.service.ServiceException;
import com.epam.esm.service.UsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    private static final Logger LOG = Logger.getLogger(UsersServiceImpl.class);
    private final UsersDAOImpl usersDAO;

    @Autowired
    public UsersServiceImpl(UsersDAOImpl usersDAO) {
        this.usersDAO = usersDAO;
    }

    @Override
    public List<User> getUsers(Integer page) throws ServiceException {
        return usersDAO.getAll(page);
    }

    @Override
    public Optional<User> getUser(int orderId) throws ServiceException {
        if (!isUserExist(orderId)) {
            throw new ServiceException("Cant find user by order id: " + orderId);
        }
        return usersDAO.get(orderId);
    }

    @Override
    public boolean isUserExist(Integer id) throws ServiceException {
        try {
            return usersDAO.isUserExist(id);
        } catch (DAOException e) {
            throw new ServiceException("Check certificate fail", e);
        }
    }
}
