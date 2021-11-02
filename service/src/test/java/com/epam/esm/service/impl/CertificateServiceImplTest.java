package com.epam.esm.service.impl;

import com.epam.esm.dao.DAOException;
import com.epam.esm.dao.impl.CertificatesDAOImpl;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {
    private static final Certificate notValidCertificate = new Certificate(0, null, null, null, 0);
    private static final Certificate validCertificate = new Certificate(5, "test", "description", 2.0, 2);
    @Mock
    private TagServiceImpl tagService;
    @Mock
    private CertificatesDAOImpl certificatesDAO;
    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Test
    void createCertificate() throws ServiceException {
        certificateService.createCertificate(validCertificate);
        Mockito.verify(certificatesDAO).save(validCertificate);
    }

    @Test
    void createCertificatesNotValidParam() {
        assertThrows(ServiceException.class, () -> certificateService.createCertificate(notValidCertificate), "CreateCertificate fail due to null value of params.");
    }

    @Test
    void deleteCertificates() throws Exception {
        Mockito.when(certificateService.isCertificateExist(validCertificate.getId())).thenReturn(false);
        certificateService.deleteCertificate(5);
        Mockito.verify(certificatesDAO).delete(validCertificate.getId());
    }

    @Test
    void deleteCertificatesExc() throws Exception {
        Mockito.when(certificateService.isCertificateExist(validCertificate.getId())).thenReturn(true);
        assertThrows(ServiceException.class, () -> certificateService.deleteCertificate(validCertificate.getId()), "CreateCertificate fail due to null value of params.");
    }

    @Test
    void updateCertificates() throws Exception {
        Mockito.when(certificateService.isCertificateExist(validCertificate.getId())).thenReturn(false);
        certificateService.updateCertificate(validCertificate);
        Mockito.verify(certificatesDAO).update(validCertificate);
    }

    @Test
    void updateCertificatesExc() throws Exception {
        Mockito.when(certificateService.isCertificateExist(validCertificate.getId())).thenReturn(true);
        assertThrows(ServiceException.class, () -> certificateService.updateCertificate(validCertificate), "CreateCertificate fail due to null value of params.");
    }

    @Test
    void updateCertificatesSingleField() throws ServiceException, DAOException {
        Mockito.when(certificateService.isCertificateExist(validCertificate.getId())).thenReturn(false);
        certificateService.updateCertificatesSingleField("volumn", "fee", 5);
        Mockito.verify(certificatesDAO).updateCertificatesSingleField("volumn", "fee", 5);
    }

    @Test
    void updateCertificatesSingleFieldExc() throws ServiceException, DAOException {
        Mockito.doThrow(new DAOException()).when(certificatesDAO).updateCertificatesSingleField("volumn", "fee", 5);
        assertThrows(ServiceException.class, () -> certificateService.updateCertificatesSingleField("volumn", "fee", 5), "CreateCertificate fail due to null value of params.");
    }

    @Test
    void updateCertificatesSingleFieldNotExist() throws ServiceException, DAOException {
        Mockito.when(certificateService.isCertificateExist(validCertificate.getId())).thenReturn(true);
        assertThrows(ServiceException.class, () -> certificateService.updateCertificatesSingleField("volumn", "fee", 5), "CreateCertificate fail due to null value of params.");
    }

    @Test
    void getCertificates() throws ServiceException {
        certificateService.getCertificates(1);
        Mockito.verify(certificatesDAO).getAll(1);
    }

    @Test
    void getCertificate() throws ServiceException {
        certificateService.getCertificate(1);
        Mockito.verify(certificatesDAO).get(1);
    }

    @Test
    void isCertificateExist() throws ServiceException, DAOException {
        certificateService.isCertificateExist(1);
        Mockito.verify(certificatesDAO).isCertificateExist(1);
    }

    @Test
    void getCertificatesByTag() throws DAOException, ServiceException {
        Mockito.when(tagService.isTagExist(1)).thenReturn(true);
        certificateService.getCertificatesByTag(1, 1);
        Mockito.verify(certificatesDAO).getCertificatesByTag(1, 1);
    }

    @Test
    void getCertificatesByTagExc() throws DAOException, ServiceException {
        Mockito.when(tagService.isTagExist(1)).thenReturn(true);
        Mockito.doThrow(new DAOException()).when(certificatesDAO).getCertificatesByTag(1, 1);
        assertThrows(ServiceException.class, () -> certificateService.getCertificatesByTag(1, 1), "Expected ServiceException");
    }

    @Test
    void getCertificatesByNamePart() throws DAOException, ServiceException {
        certificateService.getCertificatesByNamePart(1, "test");
        Mockito.verify(certificatesDAO).getCertificatesByNamePart(1, "test");
    }

    @Test
    void getCertificatesByNamePartExc() throws DAOException {
        Mockito.doThrow(new DAOException()).when(certificatesDAO).getCertificatesByNamePart(1, "test");
        assertThrows(ServiceException.class, () -> certificateService.getCertificatesByNamePart(1, "test"), "Expected ServiceException");
    }

    @Test
    void getCertificatesSortedByPrice() throws DAOException, ServiceException {
        String param = "name";
        String direction = "asc";
        certificateService.getCertificatesSorted(1, param, direction);
        Mockito.verify(certificatesDAO).getCertificatesSorted(1, param, direction);
    }

    @Test
    void getCertificatesSortedByPriceExc() throws DAOException {
        String param = "name";
        String direction = "asc";
        Mockito.doThrow(new DAOException()).when(certificatesDAO).getCertificatesSorted(1, param, direction);
        assertThrows(ServiceException.class, () -> certificateService.getCertificatesSorted(1, param, direction), "Expected ServiceException");
    }

    @Test
    void isCertificateExistExc() throws ServiceException, DAOException {
        Mockito.doThrow(new DAOException()).when(certificatesDAO).isCertificateExist(5);
        assertThrows(ServiceException.class, () -> certificateService.isCertificateExist(5), "UpdateCertificate fail");
    }
}