package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.exception.UnprocessableEntityException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * User: javawebinar.topjava
 */
@RestController
@RequestMapping("/ajax/profile/meals")
public class UserMealAjaxController extends AbstractUserMealController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> update(@Valid UserMeal meal, BindingResult result) {
        if (result.hasErrors()) {
            // TODO change to exception handler
            StringBuilder sb = new StringBuilder();
            result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
            throw new UnprocessableEntityException(sb.toString());
            //return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            if (meal.getId() == 0) {
                super.create(meal);
            } else {
                super.update(meal, meal.getId());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public List<UserMealWithExceed> filter(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        LocalDate startLD = StringUtils.isEmpty(startDate) ? LocalDate.of(1950,1,1) : LocalDate.parse(startDate);
        LocalDate endLD = StringUtils.isEmpty(endDate) ? LocalDate.of(2050,1,1) : LocalDate.parse(endDate);
        LocalTime startLT = StringUtils.isEmpty(startTime) ? LocalTime.MIN : LocalTime.parse(startTime);
        LocalTime endLT = StringUtils.isEmpty(endTime) ? LocalTime.MAX : LocalTime.parse(endTime);
        return super.getBetween(startLD, startLT, endLD, endLT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserMeal get(@PathVariable("id") int id) {
        return super.get(id);
    }
}
