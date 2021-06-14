package com.leverx.pets.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

public class ValidatorUtil {

    public static <T> void validate(T data, Validator validator) {
        Set<ConstraintViolation<T>> violations = validator.validate(data);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
