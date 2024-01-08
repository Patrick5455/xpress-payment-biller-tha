package com.xpresspayment.takehometest.common.annotations.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.xpresspayment.takehometest.common.annotations.constraints.ValidPassword;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /***
     * @apiNote
     * Must have at least one numeric character
     * Must have at least one lowercase character
     * Must have at least one uppercase character
     * Must have at least one special symbol among @#$%
     * Password length should be between 8 and 20
     */
    @Override
    public boolean isValid(@Valid @NotBlank(message = "password cannot be blank")String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null)
            return false;

        String validationRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[#@$!%*?&]).{8,20}$";
        boolean validPassword = isValidPassword(s,validationRegex);
        log.info("user password is valid : {}", validPassword);
        return validPassword;
    }

    public static boolean isValidPassword(String password,String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
