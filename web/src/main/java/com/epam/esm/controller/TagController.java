package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.ServiceException;
import com.epam.esm.service.impl.TagServiceImpl;
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
 * TagController
 * Spring rest controller
 * receives requests with /tag mapping
 */
@Component
@RestController
@RequestMapping("/tags")
public class TagController {
    private static final Logger LOG = Logger.getLogger(TagController.class);
    private final TagServiceImpl tagService;

    @Autowired
    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    /**
     * createTag, RequestMethod.POST
     * receives requests with /new mapping
     *
     * @return ResponseEntity<List < Tag>>
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTag(@RequestBody @Validated Tag tag) throws ServiceException {
        tagService.createTag(tag.getName());
        return new ResponseEntity<>("New tag created.", HttpStatus.OK);
    }

    /**
     * getTags, RequestMethod.GET
     * receives requests with /list mapping
     *
     * @return ResponseEntity<List < Tag>>
     */
    @RequestMapping(value = "/page/{page}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTags(@PathVariable Integer page) throws ServiceException {
        List<Tag> tagList = tagService.getTags(page);
        for (Tag tag : tagList) {
            Integer id = tag.getId();
            Link selfLink = linkTo(TagController.class).slash(id).withSelfRel();
            Link delete = linkTo(TagController.class).slash(id).withRel("delete");
            tag.add(selfLink);
            tag.add(delete);
        }
        Link link = linkTo(TagController.class).withSelfRel();
        CollectionModel<Tag> result = CollectionModel.of(tagList, link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * getTags, RequestMethod.GET
     * receives requests with /list mapping
     *
     * @return ResponseEntity<List < Tag>>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTag(@PathVariable Optional<Integer> id) throws ServiceException {
        Optional<Tag> tag = tagService.getTag(id.get());
        Link delete = linkTo(TagController.class).slash(id).withRel("delete");
        Link getAll = linkTo(TagController.class).slash("page").slash(0).withRel("getAll");
        tag.get().add(delete);
        tag.get().add(getAll);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    /**
     * deleteTag, RequestMethod.DELETE
     * receives requests with /id mapping
     *
     * @return ResponseEntity<List < Tag>>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTag(@PathVariable Optional<Integer> id) throws ServiceException {
        tagService.deleteTag(id.get());
        return new ResponseEntity<>("Tag with id: " + id.get() + " deleted.", HttpStatus.OK);
    }
}
