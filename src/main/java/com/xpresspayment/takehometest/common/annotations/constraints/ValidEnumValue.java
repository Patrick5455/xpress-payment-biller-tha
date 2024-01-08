

package com.xpresspayment.takehometest.common.annotations.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.xpresspayment.takehometest.common.annotations.validators.ValidEnumValueValidator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

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
