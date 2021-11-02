package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(Integer id) throws DAOException;

    List<T> getAll(Integer page) throws DAOException;

    void save(T t) throws DAOException;

    void update(T t) throws DAOException;

    void delete(Integer id) throws DAOException;
}
