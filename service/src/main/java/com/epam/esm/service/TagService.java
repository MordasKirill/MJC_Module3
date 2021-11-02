package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    /**
     * @param name to create new tag
     * @throws ServiceException exception in Service layer
     */
    void createTag(String name) throws ServiceException;

    /**
     * @param id to delete chosen tag
     * @throws ServiceException exception in Service layer
     */
    void deleteTag(Integer id) throws ServiceException;

    /**
     * @return List of tag entity's
     */
    List<Tag> getTags(Integer page) throws ServiceException;

    /**
     * @return single tag entity
     */
    Optional<Tag> getTag(Integer id) throws ServiceException;

    /**
     * @param id id to be checked
     * @return true/false is exists
     * @throws ServiceException exception in Service layer
     */
    boolean isTagExist(Integer id) throws ServiceException;
}
