package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    private static final LoggerWrapper LOG = LoggerWrapper.get(UserMealServiceImpl.class);


    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal create(UserMeal userMeal) {
        return repository.save(userMeal);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        if (LoggedUser.id() != ExceptionUtil.check(repository.get(id), id).getUser().getId()) {
            throw LOG.getNotFoundException("Not found entity for current user");
        }
        ExceptionUtil.check(repository.delete(id), id);
    }

    @Override
    public UserMeal get(int id) throws NotFoundException {
        UserMeal userMeal = ExceptionUtil.check(repository.get(id), id);
        if (LoggedUser.id() != userMeal.getUser().getId()) {
            throw LOG.getNotFoundException("Not found entity for current user");
        }
        return userMeal;
    }

    @Override
    public void update(UserMeal userMeal) throws NotFoundException {
        if (LoggedUser.id() != userMeal.getUser().getId()) {
            throw LOG.getNotFoundException("Not found entity for current user");
        }
        ExceptionUtil.check(repository.save(userMeal), userMeal.getId());
    }


    @Override
    public List<UserMeal> getAllByUserId(int userId) {
        return repository.getAllByUserId(userId);
    }
}
