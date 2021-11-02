package com.epam.esm.service.impl;

import com.epam.esm.dao.DAOException;
import com.epam.esm.dao.impl.CertificatesDAOImpl;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.ServiceException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificatesDAOImpl certificatesDAO;
    private final TagService tagService;

    @Autowired
    public CertificateServiceImpl(CertificatesDAOImpl certificatesDAO, TagService tagService) {
        this.certificatesDAO = certificatesDAO;
        this.tagService = tagService;
    }

    @Override
    public void createCertificate(Certificate certificate) throws ServiceException {
        if (Optional.ofNullable(certificate.getName()).isPresent() &&
                Optional.ofNullable(certificate.getDescription()).isPresent() &&
                certificate.getPrice() != 0 &&
                certificate.getDuration() != 0) {
            certificatesDAO.save(certificate);
        } else {
            throw new ServiceException("CreateCertificate fail due to null value of params. " + certificate);
        }
    }

    @Override
    public void deleteCertificate(Integer id) throws ServiceException {
        if (!Optional.ofNullable(id).isPresent() || isCertificateExist(id)) {
            throw new ServiceException("Cant find certificate with id: " + id);
        } else {
            certificatesDAO.delete(id);
        }
    }

    @Override
    public void updateCertificate(Certificate certificate) throws ServiceException {
        if (isCertificateExist(certificate.getId())) {
            throw new ServiceException("Cant find certificate with id:" + certificate.getId());
        }
        certificatesDAO.update(certificate);
    }

    @Override
    public void updateCertificatesSingleField(String column, Object value, Integer id) throws ServiceException {
        try {
            if (!Optional.ofNullable(id).isPresent()
                    || !Optional.ofNullable(value).isPresent()
                    || isCertificateExist(id)) {
                throw new ServiceException("Cant find certificate with id:" + id);
            }
            certificatesDAO.updateCertificatesSingleField(column, value, id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Certificate> getCertificates(int page) throws ServiceException {
        return certificatesDAO.getAll(page);
    }

    @Override
    public Optional<Certificate> getCertificate(Integer id) throws ServiceException {
        if (!Optional.ofNullable(id).isPresent() || isCertificateExist(id)) {
            throw new ServiceException("Cant find certificate with id:" + id);
        }
        return certificatesDAO.get(id);
    }

    @Override
    public List<Certificate> getCertificatesByTag(Integer page, Integer id) throws ServiceException {
        try {
            if (!Optional.ofNullable(id).isPresent() || !tagService.isTagExist(id)) {
                throw new ServiceException("Cant find tag with id: " + id);
            }
            return certificatesDAO.getCertificatesByTag(page, id);
        } catch (DAOException e) {
            throw new ServiceException("Fail to get certificates  by tag.", e);
        }
    }

    @Override
    public List<Certificate> getCertificatesBySeveralTags(Integer page, Integer firstTagId, Integer secondTagId) throws ServiceException {
        try {
            if (!Optional.ofNullable(firstTagId).isPresent()
                    || !Optional.ofNullable(secondTagId).isPresent()
                    || !tagService.isTagExist(firstTagId)
                    || !tagService.isTagExist(secondTagId)) {
                throw new ServiceException("Cant find tag with id: " + firstTagId + " or " + secondTagId);
            }
            return certificatesDAO.getCertificatesBySeveralTags(page, firstTagId, secondTagId);
        } catch (DAOException e) {
            throw new ServiceException("Fail to get certificates  by tag.", e);
        }
    }

    @Override
    public List<Certificate> getCertificatesByNamePart(Integer page, String name) throws ServiceException {
        try {
            return certificatesDAO.getCertificatesByNamePart(page, name);
        } catch (DAOException e) {
            throw new ServiceException("Fail to get certificates  by name.", e);
        }
    }

    @Override
    public List<Certificate> getCertificatesSorted(Integer page, String sortParam, String direction) throws ServiceException {
        try {
            return certificatesDAO.getCertificatesSorted(page, sortParam, direction);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isCertificateExist(Integer id) throws ServiceException {
        try {
            return certificatesDAO.isCertificateExist(id);
        } catch (DAOException e) {
            throw new ServiceException("Check certificate fail", e);
        }
    }
}
