package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

/**
 * Created by msamichev on 11.07.2015.
 */
@ActiveProfiles(inheritProfiles = false, value = {"datajpa", "postgres"})
public class DataJpaUserMealServiceTest extends UserMealServiceTest {
}
