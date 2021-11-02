package com.epam.esm.dao.impl;

import com.epam.esm.dao.DAOException;
import com.epam.esm.dao.Dao;
import com.epam.esm.entity.Certificate;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificatesDAOImpl implements Dao<Certificate> {
    private static final Logger LOG = Logger.getLogger(CertificatesDAOImpl.class);
    private static final String UPDATE_CERTIFICATE = "UPDATE Certificate set %s = :value where id = :id";
    private static final String SELECT_FROM_CERTIFICATE_WHERE_ASC = "from Certificate order by %s %s";
    private final SessionFactory sessionFactory;

    @Autowired
    public CertificatesDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Certificate> getAll(Integer page) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<Certificate> criteriaQuery = session.createQuery("from Certificate", Certificate.class);
        return criteriaQuery.setFirstResult(page * 10).setMaxResults(10).getResultList();
    }

    @Override
    public Optional<Certificate> get(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        return Optional.of(session.get(Certificate.class, id));
    }

    @Override
    @Transactional
    public void save(Certificate certificate) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(certificate);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<?> criteriaQuery = session.createQuery("DELETE FROM Certificate WHERE id = :cerfId");
        criteriaQuery.setParameter("cerfId", id);
        criteriaQuery.executeUpdate();
    }

    @Override
    @Transactional
    public void update(Certificate certificate) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<?> criteriaQuery = session.createQuery("UPDATE Certificate set name = :name, price = :price, description = :description where id = :id");
        criteriaQuery.setParameter("name", certificate.getName());
        criteriaQuery.setParameter("price", certificate.getPrice());
        criteriaQuery.setParameter("description", certificate.getDescription());
        criteriaQuery.setParameter("id", certificate.getId());
        criteriaQuery.executeUpdate();
    }

    @Transactional
    public void updateCertificatesSingleField(String column, Object value, Integer id) throws DAOException {
        Session session = this.sessionFactory.getCurrentSession();
        Query<?> criteriaQuery = session.createQuery(String.format(UPDATE_CERTIFICATE, column));
        criteriaQuery.setParameter("value", value);
        criteriaQuery.setParameter("id", id);
        criteriaQuery.executeUpdate();
    }

    public List<Certificate> getCertificatesByTag(Integer page, Integer id) throws DAOException {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Certificate> criteriaQuery = session.createQuery("from Certificate cerf join FETCH cerf.tags tags where tags.id = :id", Certificate.class);
        criteriaQuery.setParameter("id", id);
        return criteriaQuery.setFirstResult(page * 10).setMaxResults(10).getResultList();
    }

    public List<Certificate> getCertificatesBySeveralTags(Integer page, Integer firstTagId, Integer secondTagId) throws DAOException {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Certificate> criteriaQuery = session.createQuery("from Certificate cerf join FETCH cerf.tags tags where tags.id in (?1, ?2)", Certificate.class);
        criteriaQuery.setParameter(1, firstTagId);
        criteriaQuery.setParameter(2, secondTagId);
        return criteriaQuery.setFirstResult(page * 10).setMaxResults(10).getResultList();
    }

    public List<Certificate> getCertificatesByNamePart(Integer page, String name) throws DAOException {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Certificate> criteriaQuery = session.createQuery("from Certificate where name like :name", Certificate.class);
        criteriaQuery.setParameter("name", name);
        return criteriaQuery.setFirstResult(page * 10).setMaxResults(10).getResultList();
    }

    public List<Certificate> getCertificatesSorted(Integer page, String sortParam, String direction) throws DAOException {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Query<Certificate> criteriaQuery = session.createQuery(String.format(SELECT_FROM_CERTIFICATE_WHERE_ASC, sortParam, direction), Certificate.class);
            return criteriaQuery.setFirstResult(page * 10).setMaxResults(10).getResultList();
        } catch (Exception e) {
            throw new DAOException(e.getCause().toString());
        }
    }

    public boolean isCertificateExist(Integer id) throws DAOException {
        Session session = this.sessionFactory.getCurrentSession();
        Query<?> criteriaQuery = session.createQuery("FROM Certificate WHERE id = :id");
        criteriaQuery.setParameter("id", id);
        return criteriaQuery.list().size() == 0;
    }
}
