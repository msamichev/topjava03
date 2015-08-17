package ru.javawebinar.topjava.util.exception;

/**
 * Created by msamichev on 17.08.2015.
 */
public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException(String message) {
        super(message);
    }
}