package com.example.athleticore.utils.validation;

import com.example.athleticore.service.impl.calendar.DateIsNotHolidayValidate;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateIsNotHolidayValidate.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DateIsNotHolidayValidator {
    String message() default "Date is a holiday and cannot be selected for a session.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
