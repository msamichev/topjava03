package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.User;

/**
 * GKislin
 * 06.03.2015.
 */
public class LoggedUser {

    private static User user;

    public static int id() {
        return user.getId();
    }

    public static int getCaloriesPerDay() {
        return user.getCalories();
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LoggedUser.user = user;
    }
}
