package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(UserMealRestController.REST_URL)
public class UserMealRestController extends AbstractUserMealController {
    static final String REST_URL = "/rest/meals";

    private static final LoggerWrapper LOG = LoggerWrapper.get(UserMealRestController.class);


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserMeal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public void deleteAll() {
        super.deleteAll();
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody UserMeal userMeal) {
        super.update(userMeal);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMeal> createNew(@RequestBody UserMeal userMeal) {
        UserMeal created = super.create(userMeal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriOfNewResource);

        return new ResponseEntity<>(created, httpHeaders, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/between", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getBetween(@RequestParam("startDateTime") String startDT, @RequestParam("endDateTime") String
            endDT) {

        LocalDateTime startDateTime = TimeUtil.toDateTime(startDT, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime endDateTime = TimeUtil.toDateTime(endDT, DateTimeFormatter.ISO_DATE_TIME);
        return super.getBetween(startDateTime.toLocalDate(),startDateTime.toLocalTime(),endDateTime.toLocalDate(),endDateTime.toLocalTime());
    }
}