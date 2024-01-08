/*
 * Copyright (c) 2022. Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 *  Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 *    confidential  Written by Patrick Ojunde <p@revnorth.io>
 */

package io.revnorth.commonannotations.constraints;

import io.revnorth.commonannotations.contraintvalidators.ValidEnumValueValidator;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidEnumValueValidator.class)
@Documented
public @interface ValidEnumValue {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message()  default "Not a valid type";
    Class<?> enumClass();
}
