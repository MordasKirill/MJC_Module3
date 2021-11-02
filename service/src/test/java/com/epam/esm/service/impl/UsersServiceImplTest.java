package com.epam.esm.service.impl;

import com.epam.esm.dao.DAOException;
import com.epam.esm.dao.impl.UsersDAOImpl;
import com.epam.esm.service.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {
    @Mock
    private UsersDAOImpl usersDAO;
    @InjectMocks
    private UsersServiceImpl usersService;

    @Test
    void getUsers() throws ServiceException {
        usersService.getUsers(1);
        Mockito.verify(usersDAO).getAll(1);
    }

    @Test
    void getUser() throws ServiceException {
        Mockito.when(usersService.isUserExist(1)).thenReturn(true);
        usersService.getUser(1);
        Mockito.verify(usersDAO).get(1);
    }

    @Test
    void isUserExist() throws ServiceException, DAOException {
        usersService.isUserExist(1);
        Mockito.verify(usersDAO).isUserExist(1);
    }

    @Test
    void isUserExistExc() throws ServiceException, DAOException {
        Mockito.doThrow(new DAOException()).when(usersDAO).isUserExist(5);
        assertThrows(ServiceException.class, () -> usersService.isUserExist(5), "UpdateCertificate fail");
    }
}