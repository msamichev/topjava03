package ru.javawebinar.topjava.util;

import com.sun.org.apache.xpath.internal.SourceTree;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 500)
        );
        List list = getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 1000);
//        .toLocalDate();
//        .toLocalTime();

    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> countCalariesPerDay = mealList.stream()
                .collect(Collectors.groupingBy(s -> s.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));



        return mealList.stream()
                .filter((s) -> s.getDateTime().toLocalTime().isAfter(startTime) && s.getDateTime().toLocalTime().isBefore(endTime))
                .map(s -> new UserMealWithExceed(s.getDateTime(), s.getDescription(), s.getCalories(),
                        countCalariesPerDay.get(s.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
//
//        return mealList.stream()
//                .filter((s) -> s.getDateTime().toLocalTime().isAfter(startTime) && s.getDateTime().toLocalTime().isBefore(endTime))
//                .map(s -> new UserMealWithExceed(s.getDateTime(), s.getDescription(), s.getCalories(),
//                        mealList.stream()
//                                .filter((b) -> b.getDateTime().toLocalDate().equals(s.getDateTime().toLocalDate()))
//                                .mapToInt((b) -> b.getCalories()).sum() > caloriesPerDay))
//                .collect(Collectors.toList());


    }
}
