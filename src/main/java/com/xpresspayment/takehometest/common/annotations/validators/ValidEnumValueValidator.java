

package com.xpresspayment.takehometest.common.annotations.validators;

import java.lang.reflect.Field;
import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.xpresspayment.takehometest.common.annotations.constraints.ValidEnumValue;


public class ValidEnumValueValidator implements ConstraintValidator<ValidEnumValue, String> {

    private Class<?> enumClass;

    @Override
    public void initialize(ValidEnumValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(@Valid @NotBlank(message = "enum value cannot be blank")String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null){
            return true;
        }
       return Arrays.stream(enumClass.getFields()).map(Field::getName).anyMatch(
               fn -> fn.equalsIgnoreCase(value));
    }
}
