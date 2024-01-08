package com.xpresspayment.takehometest.common.annotations.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.xpresspayment.takehometest.common.annotations.constraints.ValidPhoneNumber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(@Valid @NotBlank(message = "phone number cannot be blank") String userPhoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (userPhoneNumber == null)
            return true;
       PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
       Phonenumber.PhoneNumber phoneNumber;
        try {
           phoneNumber = phoneNumberUtil.parse(userPhoneNumber,
                   Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITHOUT_PLUS_SIGN.name());
        } catch (NumberParseException e) {
           return false;
        }
        return phoneNumberUtil.isValidNumber(phoneNumber);
    }
}
