package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

/**
 * Created by msamichev on 11.07.2015.
 */
@ActiveProfiles(inheritProfiles = false, value = {"jpa", "postgres"})
public class JpaUserServiceTest extends UserServiceTest {
}