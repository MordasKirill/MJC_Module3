package com.epam.esm.service;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {

    /**
     * @return List of user entity's
     */
    List<User> getUsers(Integer page) throws ServiceException;

    /**
     * @return Get single user entity
     */
    Optional<User> getUser(int id) throws ServiceException;

    /**
     * @param id id to be checked
     * @return true/false is exists
     * @throws ServiceException exception in Service layer
     */
    boolean isUserExist(Integer id) throws ServiceException;
}
