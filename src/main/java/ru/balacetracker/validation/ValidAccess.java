package ru.balacetracker.validation;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AccessValidator.class)
public @interface ValidAccess {
    Type type() default Type.OBJECT;

    enum Type {
        OBJECT,
        TRANSACTION_ID,
        TRANSACTION_ACCOUNT_ID,
    }

}
