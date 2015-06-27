package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final UserMeal USER_MEAL1 = new UserMeal(BaseEntity.START_SEQ + 2, LocalDateTime.of(2015, 6, 25, 10, 30), "description1", 555);
    public static final UserMeal USER_MEAL2 = new UserMeal(BaseEntity.START_SEQ + 3, LocalDateTime.of(2015, 6, 27, 19, 0), "description2", 1000);
    public static final UserMeal ADMIN_MEAL1 = new UserMeal(BaseEntity.START_SEQ + 4, LocalDateTime.of(2015, 6, 26, 6, 0), "description3", 555);
    public static final UserMeal ADMIN_MEAL2 = new UserMeal(BaseEntity.START_SEQ + 5, LocalDateTime.of(2015, 6, 27, 15, 25), "description4", 666);

    //public static final TestUser USER = new TestUser(BaseEntity.START_SEQ, "User", "user@yandex.ru", "password", true, Role.ROLE_USER);
    //public static final User ADMIN = new TestUser(BaseEntity.START_SEQ + 1, "Admin", "admin@gmail.com", "admin", true, Role.ROLE_ADMIN);


}
