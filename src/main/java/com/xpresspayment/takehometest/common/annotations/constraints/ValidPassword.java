package io.revnorth.commonannotations.constraints;


import static io.revnorth.utils.Constants.PASSWORD_VALIDATION_MESSAGE;
import io.revnorth.commonannotations.contraintvalidators.ValidPasswordValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPasswordValidator.class)
@Documented
public @interface ValidPassword {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message()  default PASSWORD_VALIDATION_MESSAGE;

}
