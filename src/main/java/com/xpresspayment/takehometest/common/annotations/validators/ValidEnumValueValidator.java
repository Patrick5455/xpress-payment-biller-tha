/*
 * Copyright (c) 2022. Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 *  Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 *    confidential  Written by Patrick Ojunde <p@revnorth.io>
 */

package io.revnorth.commonannotations.contraintvalidators;

import io.revnorth.commonannotations.constraints.ValidEnumValue;
import java.lang.reflect.Field;
import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class ValidEnumValueValidator implements ConstraintValidator<ValidEnumValue, String> {

    private Class<?> enumClass;

    @Override
    public void initialize(ValidEnumValue constraintAnnotation) {
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
