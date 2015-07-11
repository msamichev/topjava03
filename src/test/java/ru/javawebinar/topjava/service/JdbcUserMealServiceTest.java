package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by msamichev on 11.07.2015.
 */
@ActiveProfiles(inheritProfiles = false, value = {"jdbc", "postgres"})
public class JdbcUserMealServiceTest extends UserMealServiceTest {
}
