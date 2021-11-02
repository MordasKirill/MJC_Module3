package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.ServiceException;
import com.epam.esm.service.impl.OrdersServiceImpl;
import com.epam.esm.service.impl.UsersServiceImpl;
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

@Component
@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOG = Logger.getLogger(TagController.class);
    private final UsersServiceImpl usersService;
    private final OrdersServiceImpl ordersService;

    @Autowired
    public UserController(UsersServiceImpl usersService, OrdersServiceImpl ordersService) {
        this.usersService = usersService;
        this.ordersService = ordersService;
    }

    /**
     * getUsers, RequestMethod.GET
     * receives requests with /page/{page}/ mapping
     *
     * @return ResponseEntity<List < User>>
     */
    @RequestMapping(value = "/page/{page}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers(@PathVariable Integer page) throws ServiceException {
        List<User> certificateList = usersService.getUsers(page);
        for (User user : certificateList) {
            Integer id = user.getId();
            Link selfLink = linkTo(UserController.class).slash(id).withSelfRel();
            user.add(selfLink);
        }
        Link link = linkTo(UserController.class).withSelfRel();
        CollectionModel<User> result = CollectionModel.of(certificateList, link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * getUsersById, RequestMethod.GET
     * receives requests with /{id} mapping
     *
     * @return ResponseEntity<List < User>>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@PathVariable int id) throws ServiceException {
        Optional<User> user = usersService.getUser(id);
        Link delete = linkTo(UserController.class).slash(id).withRel("delete");
        Link getAll = linkTo(UserController.class).slash("page").slash(0).withRel("getAll");
        user.get().add(delete);
        user.get().add(getAll);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * getUsersById, RequestMethod.GET
     * receives requests with /{id} mapping
     *
     * @return ResponseEntity<List < User>>
     */
    @RequestMapping(value = "/frequentlyUsedTag/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMostFrequentTagHighestPrice(@PathVariable Optional<Integer> userId) throws ServiceException {
        return new ResponseEntity<>(ordersService.getMaxAndPriceMostFrequentTags(userId.get()), HttpStatus.OK);
    }

    /**
     * getOrderByUserIdOrderId, RequestMethod.GET
     * receives requests with /orderId/ mapping
     *
     * @return ResponseEntity<List < User>>
     */
    @RequestMapping(value = "/orderId/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOrderByUserIdOrderId(@RequestParam int userId, @RequestParam Optional<Integer> orderId) throws ServiceException {
        Order order = ordersService.getOrderByUserIdOrderId(userId, orderId.get());
        Link delete = linkTo(UserController.class).slash(userId).withRel("delete");
        Link getAll = linkTo(UserController.class).slash("page").slash(0).withRel("getAll");
        order.add(delete);
        order.add(getAll);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    /**
     * getUsersById, RequestMethod.POST
     * receives requests with /new mapping
     *
     * @return ResponseEntity<List < User>>
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrder(@RequestBody @Validated Order order) throws ServiceException {
        ordersService.createOrder(order);
        return new ResponseEntity<>("Order created.", HttpStatus.OK);
    }
}
