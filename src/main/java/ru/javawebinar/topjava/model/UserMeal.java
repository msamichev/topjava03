package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@Entity
@Table(name = "MEALS")
@NamedQueries({
        @NamedQuery(name = UserMeal.GET, query = "SELECT um FROM UserMeal um WHERE um.id=:id and um.user.id=:userId"),
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal um WHERE um.id=:id and um.user.id=:userId"),
        @NamedQuery(name = UserMeal.DELETE_ALL, query = "DELETE FROM UserMeal um WHERE um.user.id=:userId"),
        @NamedQuery(name = UserMeal.ALL, query = "SELECT um FROM UserMeal um JOIN um.user u WHERE u.id=:userId"),
        @NamedQuery(name = UserMeal.ALL_BETWEEN, query = "SELECT um FROM UserMeal um JOIN um.user u WHERE u" +
                ".id=:userId and um.dateTime BETWEEN :startDate AND :endDate")
})
public class UserMeal extends BaseEntity {


    public static final String GET = "UserMeal.get";
    public static final String DELETE = "UserMeal.delete";
    public static final String DELETE_ALL = "UserMeal.deleteAll";
    public static final String ALL = "UserMeal.all";
    public static final String ALL_BETWEEN = "UserMeal.allBetween";

    @Column(name = "datetime", nullable = false)
    @NotEmpty
    protected LocalDateTime dateTime;

    @Column(name = "description", nullable = true)
    protected String description;

    @Column(name = "calories", nullable = false)
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public UserMeal() {
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal(" + id + ", " + dateTime + ", '" + description + "', calories:" + calories + ')';
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
