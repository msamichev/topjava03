package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by msamichev on 11.07.2015.
 */
@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Query("SELECT m FROM UserMeal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<UserMeal> findAllByUser(@Param("userId") int userId);

    @Query("SELECT m FROM UserMeal m WHERE m.id=:id AND m.user.id=:userId")
    UserMeal get(@Param("id") int id, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Transactional
    @Modifying
    int deleteByIdAndUserId(int id, int userId);


    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal i WHERE i.user.id=:userId")
    int deleteAll(@Param("userId") int userId);


    List<UserMeal> findByDateTimeBetweenAndUserIdOrderByDateTimeDesc(LocalDateTime startDate, LocalDateTime endDate, int userId);
}
