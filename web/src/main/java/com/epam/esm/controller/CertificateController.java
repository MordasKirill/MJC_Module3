package com.epam.esm.controller;

import com.epam.esm.entity.Certificate;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * CertificateController
 * Spring rest controller
 * receives requests with /certificate mapping
 */
@Component
@RestController
@RequestMapping("/certificates")
public class CertificateController {
    private static final Logger LOG = Logger.getLogger(CertificateController.class);
    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * createCertificate RequestMethod.POST
     * receives requests with /new mapping
     *
     * @return ResponseEntity<List < Certificate>>
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCertificate(@RequestBody @Validated Certificate certificate) throws ServiceException {
        certificateService.createCertificate(certificate);
        return new ResponseEntity<>("Certificate created.", HttpStatus.OK);
    }

    /**
     * getCertificates RequestMethod.GET
     * receives requests with /list mapping
     *
     * @return ResponseEntity<List < Certificate>>
     */
    @RequestMapping(value = "/page/{page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCertificates(@PathVariable int page) throws ServiceException {
        List<Certificate> certificateList = certificateService.getCertificates(page);
        for (Certificate certificate : certificateList) {
            Integer id = certificate.getId();
            Link selfLink = linkTo(CertificateController.class).slash(id).withSelfRel();
            certificate.add(selfLink);
        }
        Link link = linkTo(CertificateController.class).withSelfRel();
        CollectionModel<Certificate> result = CollectionModel.of(certificateList, link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * getCertificates RequestMethod.GET
     * receives requests with /list mapping
     *
     * @return ResponseEntity<List < Certificate>>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCertificate(@PathVariable Optional<Integer> id) throws ServiceException {
        Optional<Certificate> certificate = certificateService.getCertificate(id.get());
        Link delete = linkTo(CertificateController.class).slash(id).withRel("delete");
        Link getAll = linkTo(CertificateController.class).slash("page").slash(0).withRel("getAll");
        certificate.get().add(delete);
        certificate.get().add(getAll);
        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }

    /**
     * deleteCertificate , RequestMethod.DELETE
     * receives requests with /id mapping
     *
     * @return ResponseEntity<List < Certificate>>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCertificate(@PathVariable Optional<Integer> id) throws ServiceException {
        certificateService.deleteCertificate(id.get());
        return new ResponseEntity<>("Certificate with id: " + id.get() + " deleted.", HttpStatus.OK);

    }

    /**
     * updateCertificate, RequestMethod.PUT
     * receives requests with /put mapping
     *
     * @return ResponseEntity<List < Certificate>>
     */
    @RequestMapping(value = {"/"}, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCertificate(@RequestBody @Validated Certificate certificate) throws ServiceException {
        certificateService.updateCertificate(certificate);
        Optional<Certificate> updatedCertificate = certificateService.getCertificate(certificate.getId());
        Link delete = linkTo(CertificateController.class).slash(certificate.getId()).withRel("delete");
        Link getAll = linkTo(CertificateController.class).slash("page").slash(0).withRel("getAll");
        updatedCertificate.get().add(delete);
        updatedCertificate.get().add(getAll);
        return new ResponseEntity<>(updatedCertificate, HttpStatus.OK);
    }

    /**
     * updateCertificate, RequestMethod.PUT
     * receives requests with /put mapping
     *
     * @return ResponseEntity<List < Certificate>>
     */
    @RequestMapping(value = {"/{id}/"}, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCertificateSingleField(@PathVariable Optional<Integer> id, @RequestParam String column, @RequestParam Object value) throws ServiceException {
        certificateService.updateCertificatesSingleField(column, value, id.get());
        Optional<Certificate> updatedCertificate = certificateService.getCertificate(id.get());
        Link delete = linkTo(CertificateController.class).slash(id.get()).withRel("delete");
        Link getAll = linkTo(CertificateController.class).slash("page").slash(0).withRel("getAll");
        updatedCertificate.get().add(delete);
        updatedCertificate.get().add(getAll);
        return new ResponseEntity<>(updatedCertificate, HttpStatus.OK);
    }

    /**
     * getCertificatesByTag, RequestMethod.GET
     * receives requests with /list/tag mapping
     *
     * @return ResponseEntity<List < Certificate>>
     */
    @RequestMapping(value = "/{page}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCertificatesByTag(@PathVariable Integer page, @PathVariable Optional<Integer> id) throws ServiceException {
        List<Certificate> certificateList = certificateService.getCertificatesByTag(page, id.get());
        for (Certificate certificate : certificateList) {
            Integer certificateId = certificate.getId();
            Link selfLink = linkTo(CertificateController.class).slash(certificateId).withSelfRel();
            Link delete = linkTo(CertificateController.class).slash(certificateId).withRel("delete");
            certificate.add(selfLink);
            certificate.add(delete);
        }
        Link link = linkTo(CertificateController.class).withSelfRel();
        CollectionModel<Certificate> result = CollectionModel.of(certificateList, link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * getCertificatesByTag, RequestMethod.GET
     * receives requests with /list/tag mapping
     *
     * @return ResponseEntity<List < Certificate>>
     */
    @RequestMapping(value = "/severalTags/{page}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCertificatesBySeveralTags(@PathVariable Integer page, @RequestParam Optional<Integer> firstId, @RequestParam Optional<Integer> secondId) throws ServiceException {
        List<Certificate> certificateList = certificateService.getCertificatesBySeveralTags(page, firstId.get(), secondId.get());
        for (Certificate certificate : certificateList) {
            Integer certificateId = certificate.getId();
            Link selfLink = linkTo(CertificateController.class).slash(certificateId).withSelfRel();
            Link delete = linkTo(CertificateController.class).slash(certificateId).withRel("delete");
            certificate.add(selfLink);
            certificate.add(delete);
        }
        Link link = linkTo(CertificateController.class).withSelfRel();
        CollectionModel<Certificate> result = CollectionModel.of(certificateList, link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * getCertificatesByPartName, RequestMethod.GET
     * receives requests with /list/name/part mapping
     *
     * @return ResponseEntity<List < Certificate>>
     */
    @RequestMapping(value = "/name/{page}/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCertificatesByPartName(@PathVariable Integer page, @PathVariable String name) throws ServiceException {
        List<Certificate> certificateList = certificateService.getCertificatesByNamePart(page, name);
        for (Certificate certificate : certificateList) {
            Integer certificateId = certificate.getId();
            Link selfLink = linkTo(CertificateController.class).slash(certificateId).withSelfRel();
            Link delete = linkTo(CertificateController.class).slash(certificateId).withRel("delete");
            certificate.add(selfLink);
            certificate.add(delete);
        }
        Link link = linkTo(CertificateController.class).withSelfRel();
        CollectionModel<Certificate> result = CollectionModel.of(certificateList, link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * getCertificatesByNameAsc, RequestMethod.GET
     * receives requests with /list/name mapping
     *
     * @return ResponseEntity<List < Certificate>>
     */
    @RequestMapping(value = "/sorting/{page}/{sortParam}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCertificatesByNameAsc(@PathVariable Integer page, @PathVariable String sortParam, @RequestParam(defaultValue = "asc") String direction) throws ServiceException {
        List<Certificate> certificateList = certificateService.getCertificatesSorted(page, sortParam, direction);
        for (Certificate certificate : certificateList) {
            Integer certificateId = certificate.getId();
            Link selfLink = linkTo(CertificateController.class).slash(certificateId).withSelfRel();
            Link delete = linkTo(CertificateController.class).slash(certificateId).withRel("delete");
            certificate.add(selfLink);
            certificate.add(delete);
        }
        Link link = linkTo(CertificateController.class).withSelfRel();
        CollectionModel<Certificate> result = CollectionModel.of(certificateList, link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
