package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

/**
 * GKislin
 * 06.03.2015.
 */
public class LoggedUser {

    private static User user =  new User(1, "userName", "email", "password", Role.ROLE_ADMIN);

    public static int id() {
        return 1;
    }

    public static int getCaloriesPerDay() {
        return 2000;
    }

    public static User getUser() {
        return user;
    }
}
