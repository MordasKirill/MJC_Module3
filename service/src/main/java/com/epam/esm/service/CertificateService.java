package com.epam.esm.service;

import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateService {
    /**
     * @param certificate entity used to create Certificate
     * @throws ServiceException exception in Service layer
     */
    void createCertificate(Certificate certificate) throws ServiceException;

    /**
     * * @param certificate entity used to delete Certificate
     * delete chosen certificate
     *
     * @throws ServiceException exception in Service layer
     */
    void deleteCertificate(Integer id) throws ServiceException;

    /**
     * @param certificate entity used to update Certificate
     *                    update info of chosen certificate
     * @throws ServiceException exception in Service layer
     */
    void updateCertificate(Certificate certificate) throws ServiceException;

    /**
     * @param column column to be updated
     * @param value  value for column to be updated
     * @param id     id of certificate to be updated
     * @throws ServiceException exception in DAO layer
     */
    void updateCertificatesSingleField(String column, Object value, Integer id) throws ServiceException;

    /**
     * @return List with Certificate entity's
     * @throws ServiceException exception in Service layer
     */
    List<Certificate> getCertificates(int page) throws ServiceException;

    /**
     * @return Single certificate with Certificate entity's
     * @throws ServiceException exception in Service layer
     */
    Optional<Certificate> getCertificate(Integer id) throws ServiceException;

    /**
     * @param id id to be checked
     * @return true/false is exists
     * @throws ServiceException exception in Service layer
     */
    boolean isCertificateExist(Integer id) throws ServiceException;

    /**
     * @param id to get certificates by tag id
     * @return List with Certificate entity's
     * @throws ServiceException exception in Service layer
     */
    List<Certificate> getCertificatesByTag(Integer page, Integer id) throws ServiceException;

    /**
     * @param firstTagId  to get certificates by tag id
     * @param secondTagId to get certificates by tag id
     * @return List with Certificate entity's
     * @throws ServiceException exception in Service layer
     */
    List<Certificate> getCertificatesBySeveralTags(Integer page, Integer firstTagId, Integer secondTagId) throws ServiceException;

    /**
     * @param name to get certificates by name
     * @return List with Certificate entity's
     * @throws ServiceException exception in Service layer
     */
    List<Certificate> getCertificatesByNamePart(Integer page, String name) throws ServiceException;

    /**
     * @return List with Certificate entity's
     * @throws ServiceException exception in Service layer
     */
    List<Certificate> getCertificatesSorted(Integer page, String sortParam, String direction) throws ServiceException;
}
