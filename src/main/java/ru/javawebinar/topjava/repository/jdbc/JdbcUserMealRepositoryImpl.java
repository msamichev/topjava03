package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    private static final RowMapper<UserMeal> ROW_MAPPER = new RowMapper(){
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserMeal userMeal = new UserMeal(rs.getInt("ID"), rs.getTimestamp("DATETIME").toLocalDateTime(),rs.getString("DESCRIPTION"),rs.getInt("CALORIES"));
            return userMeal;
        }
    };


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USER_MEALS")
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
            namedParameterJdbcTemplate.update(
                    "UPDATE user_meals SET description=:description, calories=:calories, dateTime=:dateTime, user_id=:user_id " +
                            " WHERE id=:id", map);
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM user_meals WHERE id=?", id) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return jdbcTemplate.queryForObject(
                "SELECT id, description, calories, dateTime FROM user_meals WHERE id=?",
                ROW_MAPPER, id);

    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return jdbcTemplate.query(
                "SELECT id, description, calories, dateTime FROM user_meals WHERE user_id=?",
                ROW_MAPPER, userId);
    }

    @Override
    public void deleteAll(int userId) {
        jdbcTemplate.update("DELETE FROM user_meals WHERE user_id=?", userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT id, description, calories, dateTime FROM user_meals WHERE user_id=? and datetime between ? and ?",
                ROW_MAPPER, userId, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));

    }
}
