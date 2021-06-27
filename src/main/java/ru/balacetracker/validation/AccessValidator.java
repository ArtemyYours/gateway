package ru.balacetracker.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class AccessValidator implements ConstraintValidator<ValidAccess, Object> {

    private ValidAccess.Type type;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return false;
    }

    @Override
    public void initialize(ValidAccess constraintAnnotation) {
        this.type = constraintAnnotation.type();
    }

    public void checkValidity(Object object, ValidAccess.Type type){

    }
}
