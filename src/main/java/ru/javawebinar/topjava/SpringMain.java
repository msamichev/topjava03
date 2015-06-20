package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminUserRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminUserRestController adminUserController = appCtx.getBean(AdminUserRestController.class);
            User user = new User(1, "userName", "email", "password", 2000, Role.ROLE_ADMIN);
            System.out.println(adminUserController.create(user));


            LoggedUser.setUser(user);
            UserMealRestController userMealRestController = appCtx.getBean(UserMealRestController.class);
            UserMeal userMeal = new UserMeal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, user);
            System.out.println(userMealRestController.create(userMeal));

            userMeal.setCalories(550);
            userMealRestController.update(userMeal);

            System.out.println(userMealRestController.getAll());

            System.out.println(userMealRestController.getAllByCriteria(LocalDate.MIN, LocalTime.MIN, LocalDate.MAX, LocalTime.MAX));

            userMealRestController.delete(userMeal.getId());

        }

    }
}
