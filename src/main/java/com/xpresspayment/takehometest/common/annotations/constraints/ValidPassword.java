package com.xpresspayment.takehometest.common.annotations.constraints;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.xpresspayment.takehometest.common.annotations.validators.ValidPasswordValidator;

import static com.xpresspayment.takehometest.common.utils.Constants.PASSWORD_VALIDATION_MESSAGE;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPasswordValidator.class)
@Documented
public @interface ValidPassword {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message()  default PASSWORD_VALIDATION_MESSAGE;

}
