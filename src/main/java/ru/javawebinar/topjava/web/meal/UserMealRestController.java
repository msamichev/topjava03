package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealServiceImpl;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {

    private static final LoggerWrapper LOG = LoggerWrapper.get(UserMealRestController.class);

    @Autowired
    private UserMealServiceImpl service;

    public List<UserMealWithExceed> getAllByCriteria(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        LOG.info("getByCriteria");
        return UserMealsUtil.getFilteredMealsWithExceeded(getAll(),
                startDate != null ? startDate : LocalDate.MIN,
                startTime != null ? startTime : LocalTime.MIN,
                endDate != null ? endDate : LocalDate.MAX,
                endTime != null ? endTime : LocalTime.MAX,
                LoggedUser.getCaloriesPerDay());
    }


    public List<UserMeal> getAll() {
        LOG.info("getAll");
        return service.getAllByUserId(LoggedUser.id());
    }

    public UserMeal get(int id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    public UserMeal create(UserMeal userMeal) {
        LOG.info("create " + userMeal);
        return service.create(userMeal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id);
    }

    public void update(UserMeal userMeal) {
        LOG.info("update " + userMeal);
        service.update(userMeal);
    }


}
