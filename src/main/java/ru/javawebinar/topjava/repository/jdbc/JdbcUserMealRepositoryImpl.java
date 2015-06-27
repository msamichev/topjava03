package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    private static final RowMapper<UserMeal> ROW_MAPPER = (rs, rowNum) -> {
        return new UserMeal(rs.getInt("ID"), rs.getTimestamp("DATETIME").toLocalDateTime(), rs.getString("DESCRIPTION"), rs.getInt("CALORIES"));
    };


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("MEALS")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories())
                .addValue("dateTime", Timestamp.valueOf(userMeal.getDateTime()))
                .addValue("user_id", userId);

        if (userMeal.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            userMeal.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate.update(
                    "UPDATE meals SET description=:description, calories=:calories, dateTime=:dateTime , user_id=:user_id " +
                            " WHERE id=:id AND user_id=:user_id", map) == 0) {
                userMeal = null;
            }

        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=?", id) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return jdbcTemplate.queryForObject(
                "SELECT id, description, calories, dateTime FROM meals WHERE id=? and user_id=?",
                ROW_MAPPER, id, userId);

    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return jdbcTemplate.query(
                "SELECT id, description, calories, dateTime FROM meals WHERE user_id=?",
                ROW_MAPPER, userId);
    }

    @Override
    public void deleteAll(int userId) {
        jdbcTemplate.update("DELETE FROM meals WHERE user_id=?", userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT id, description, calories, dateTime FROM meals WHERE user_id=? AND datetime BETWEEN ? AND ?",
                ROW_MAPPER, userId, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));

    }
}
