package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserService;

import java.util.List;

/**
 * Created by msamichev on 15.07.2015.
 */
@Controller
public class UserMealController {
    @Autowired
    private UserMealService service;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String userList(Model model) {
        List<UserMeal> meals = service.getAll(LoggedUser.id());
        User user = userService.get(LoggedUser.id());
        meals.stream().forEach(um -> um.setUser(user));
        model.addAttribute("mealList", meals);
        return "mealList";
    }
}
