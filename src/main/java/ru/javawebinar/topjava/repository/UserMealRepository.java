package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {

    UserMeal save(UserMeal userMeal);

    // false if not found
    boolean delete(int id);

    // null if not found
    UserMeal get(int id);


    List<UserMeal> getAllByUserId(int userId);

}
