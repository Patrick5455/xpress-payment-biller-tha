package io.revnorth.commonannotations.constraints;

import static io.revnorth.utils.Constants.PHONE_NUMBER_VALIDATION_MESSAGE;
import io.revnorth.commonannotations.contraintvalidators.PhoneNumberValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PhoneNumberValidator.class)
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message() default PHONE_NUMBER_VALIDATION_MESSAGE;

}
