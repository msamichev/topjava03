package ru.javawebinar.topjava.web.meal;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * Created by msamichev on 27.07.2015.
 */
public class UserMealRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = UserMealRestController.REST_URL + '/';

    @Autowired
    private UserMealService service;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        MealTestData.MATCHER.assertListEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), service.getAll(USER_ID));
    }

    @Test
    public void testDeleteAll() throws Exception {
        mockMvc.perform(delete(REST_URL + "all")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Assert.assertEquals(0, service.getAll(USER_ID).size());
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = MEAL1;
        updated.setDescription("update");
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, service.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MATCHER_EXCEED.contentListMatcher(UserMealsUtil.getFilteredMealsWithExceeded
                        (service.getAll(USER_ID), LocalTime.MIN, LocalTime.MAX, LoggedUser.getCaloriesPerDay())
                        .toArray(new UserMealWithExceed[0]))));

    }

    @Test
    public void testCreateNew() throws Exception {
        UserMeal expected = new UserMeal(null, LocalDateTime.of(2015, Month.MAY, 31, 23, 0), "Ужин2", 500);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        UserMeal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertListEquals(Arrays.asList(expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), service.getAll(USER_ID));

    }


    @Test
    public void testBetween() throws Exception {

        TestUtil.print(mockMvc.perform(get(REST_URL + "between?startDateTime=2015-05-30T00:00:00&endDateTime=2015-05-30T23:59:59")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MATCHER_EXCEED.contentListMatcher(UserMealsUtil.getFilteredMealsWithExceeded
                        (service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30)
                                , USER_ID), LocalTime.MIN, LocalTime.MAX, LoggedUser.getCaloriesPerDay()).toArray(new
                        UserMealWithExceed[0]))));


    }
}