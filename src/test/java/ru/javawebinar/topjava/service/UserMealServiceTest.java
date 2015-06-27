package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;


    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSave() throws Exception {
        UserMeal userMeal = new UserMeal(null, LocalDateTime.of(2015, 6, 27, 0, 0), "comment1", 1000);
        service.save(userMeal, UserTestData.USER.asUser().getId());
        MATCHER.assertListEquals(Arrays.asList(USER_MEAL1, USER_MEAL2, userMeal), service.getAll(UserTestData.USER.asUser().getId()));
    }


    @Test
    public void testGet() throws Exception {
        UserMeal userMeal = service.get(MealTestData.USER_MEAL1.getId(), UserTestData.USER.getId());
        MATCHER.assertEquals(MealTestData.USER_MEAL1, userMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundGet() throws Exception {
        UserMeal userMeal = service.get(MealTestData.USER_MEAL1.getId(), UserTestData.ADMIN.getId());

    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MealTestData.USER_MEAL1.getId(), UserTestData.USER.getId());
        MATCHER.assertListEquals(Arrays.asList(USER_MEAL2), service.getAll(UserTestData.USER.asUser().getId()));
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        MATCHER.assertListEquals(Arrays.asList(USER_MEAL1), service.getBetweenDates(LocalDate.of(2015, 1, 1), LocalDate.of(2015, 6, 26), UserTestData.USER.asUser().getId()));

    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        MATCHER.assertListEquals(Arrays.asList(ADMIN_MEAL2), service.getBetweenDateTimes(LocalDateTime.of(2015, 6, 26, 10, 0), LocalDateTime.of(2015, 6, 27, 23, 0), UserTestData.ADMIN.getId()));
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertListEquals(Arrays.asList(USER_MEAL1, USER_MEAL2), service.getAll(UserTestData.USER.asUser().getId()));
    }

    @Test
    public void testDeleteAll() throws Exception {
        service.deleteAll(UserTestData.USER.getId());
        MATCHER.assertListEquals(Collections.emptyList(), service.getAll(UserTestData.USER.asUser().getId()));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal userMeal = service.get(ADMIN_MEAL1.getId(), UserTestData.ADMIN.getId());
        userMeal.setDescription("ADMIN COMMENT");
        service.update(userMeal, UserTestData.ADMIN.getId());
        MATCHER.assertEquals(userMeal, service.get(ADMIN_MEAL1.getId(), UserTestData.ADMIN.getId()));

    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundUpdate() throws Exception {
        UserMeal userMeal = service.get(ADMIN_MEAL1.getId(), UserTestData.ADMIN.getId());
        userMeal.setDescription("ADMIN COMMENT");
        service.update(userMeal, UserTestData.USER.getId());
    }

}