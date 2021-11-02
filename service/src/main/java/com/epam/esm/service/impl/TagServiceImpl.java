package com.epam.esm.service.impl;

import com.epam.esm.dao.DAOException;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.ServiceException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private TagDAOImpl tagDAO;

    @Autowired
    public TagServiceImpl(TagDAOImpl tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public Optional<Tag> getTag(Integer id) throws ServiceException {
        if (!Optional.ofNullable(id).isPresent() || !isTagExist(id)) {
            throw new ServiceException("Cant find tag with id: " + id);
        }
        return tagDAO.get(id);
    }

    @Override
    public List<Tag> getTags(Integer page) throws ServiceException {
        return tagDAO.getAll(page);
    }

    @Override
    public void createTag(String name) throws ServiceException {
        if (!Optional.ofNullable(name).isPresent()) {
            throw new ServiceException("Name is null: " + name);
        }
        tagDAO.save(new Tag(name));
    }

    @Override
    public void deleteTag(Integer id) throws ServiceException {
        if (!Optional.ofNullable(id).isPresent() || !isTagExist(id)) {
            throw new ServiceException("Cant find tag with id: " + id);
        }
        tagDAO.delete(id);
    }

    @Override
    public boolean isTagExist(Integer id) throws ServiceException {
        try {
            return tagDAO.isTagExist(id);
        } catch (DAOException e) {
            throw new ServiceException("Check certificate fail", e);
        }
    }
}
