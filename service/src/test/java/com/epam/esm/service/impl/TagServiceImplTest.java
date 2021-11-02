package com.epam.esm.service.impl;

import com.epam.esm.dao.DAOException;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    private static final Tag tag = new Tag("dsds");
    @Mock
    private TagDAOImpl tagDAO;
    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void getTag() throws ServiceException, DAOException {
        Mockito.when(tagService.isTagExist(1)).thenReturn(true);
        tagService.getTag(1);
        Mockito.verify(tagDAO).get(1);
    }

    @Test
    void getTags() throws ServiceException, DAOException {
        tagService.getTags(1);
        Mockito.verify(tagDAO).getAll(1);
    }

    @Test
    void createTag() throws ServiceException, DAOException {
        tagService.createTag(tag.getName());
        Mockito.verify(tagDAO).save(tag);
    }

    @Test
    void createTagExc() throws DAOException {
        assertThrows(ServiceException.class, () -> tagService.createTag(null), "Expected ServiceException");
    }

    @Test
    void deleteTagExc() throws DAOException, ServiceException {
        assertThrows(ServiceException.class, () -> tagService.deleteTag(null), "Expected ServiceException");
    }

    @Test
    void deleteTag() throws ServiceException, DAOException {
        Mockito.when(tagService.isTagExist(1)).thenReturn(true);
        tagService.deleteTag(1);
        Mockito.verify(tagDAO).delete(1);
    }

    @Test
    void isTagExist() throws ServiceException, DAOException {
        tagService.isTagExist(1);
        Mockito.verify(tagDAO).isTagExist(1);
    }

    @Test
    void isTagExistExc() throws ServiceException, DAOException {
        Mockito.doThrow(new DAOException()).when(tagDAO).isTagExist(5);
        assertThrows(ServiceException.class, () -> tagService.isTagExist(5), "UpdateCertificate fail");
    }
}