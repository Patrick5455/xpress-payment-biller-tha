

package com.xpresspayment.takehometest.common.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.uuid.Generators;
import com.xpresspayment.takehometest.common.exceptions.AppException;
import com.xpresspayment.takehometest.common.exceptions.InvalidUuidException;
import com.xpresspayment.takehometest.common.exceptions.UnsupportedTimeUnitException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import static com.xpresspayment.takehometest.common.utils.Constants.DEFAULT_TIMEZONE;


@Slf4j
@Component
public class GlobalUtils {

    final static int SHORT_UUID_LENGTH = 8;
    final static int LONG_UUID_LENGTH = 16;
    final static int DEFAULT_OTP_LENGTH = 6;
    private static final int RECOVERY_CODE_LENGTH = 8;
    private static final int NUMBER_OF_RECOVERY_CODES = 10;


    public static String generateUuid() {
        return Generators.randomBasedGenerator().generate().toString().replace(
                        "-", "")
                .substring(0, LONG_UUID_LENGTH);
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static final Pattern VALID_PHONE_NUMBER_REGEX =
            Pattern.compile("^\\+\\d{1,3}\\d{1,14}$");

    public static boolean validatePhoneNumber(String phoneNumber) {
        //TODO: collect the first three chars of the string and validate it as a valid country code
        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(phoneNumber);
        return matcher.find();
    }

    public static String generateToken(int tokenLength) {
        return UUID.randomUUID().toString().replace("-", "")
                .substring(0, tokenLength);
    }

    public static String generateOtp(int len) {
        StringBuilder otp = new StringBuilder();
        for (int i = 1; i <= len; i++) {
            SecureRandom random = new SecureRandom();
            otp.append(random.nextInt(9));
        }
        return otp.toString();
    }

    public static String generateOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 1; i <= DEFAULT_OTP_LENGTH; i++) {
            SecureRandom random = new SecureRandom();
            otp.append(random.nextInt(9));
        }
        return otp.toString();
    }

    public static String validateUuid(String uuid) throws InvalidUuidException {
        try {
            return UUID.fromString(uuid).toString();
        } catch (IllegalArgumentException e) {
            throw new InvalidUuidException("invalid resource uuid");
        }
    }

    public static LocalDateTime calculateFutureDateFromNow(int age, TimeUnit timeUnit)
            throws UnsupportedTimeUnitException {
        return calculateFutureDate(LocalDateTime.now(), age, timeUnit);
    }

    public static LocalDateTime calculateFutureDate(LocalDateTime from, long age, TimeUnit timeUnit)
            throws UnsupportedTimeUnitException {
        Instant instant = Instant.now();
        switch (timeUnit) {
            case SECONDS:

                return from.plusSeconds(age);
            case MINUTES:
                return from.plusMinutes(age);
            case HOURS:
                return from.plusHours(age);
            case DAYS:
                return from.plusDays(age);
            default:
                throw new UnsupportedTimeUnitException("time unit not supported: use any from seconds to days");
        }
    }


    public static Instant calculateFutureDate(Instant from, long age, TimeUnit timeUnit)
            throws UnsupportedTimeUnitException {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(from, ZoneId.of(DEFAULT_TIMEZONE));
        switch (timeUnit) {
            case SECONDS:
                return localDateTime.plusSeconds(age).toInstant(ZoneOffset.UTC);
            case MINUTES:
                return localDateTime.plusMinutes(age).toInstant(ZoneOffset.UTC);
            case HOURS:
                return localDateTime.plusHours(age).toInstant(ZoneOffset.UTC);
            case DAYS:
                return localDateTime.plusDays(age).toInstant(ZoneOffset.UTC);
            default:
                throw new UnsupportedTimeUnitException("time unit not supported: " +
                        "use any from seconds to days");
        }
    }



    public static String getOrDefault(String key, String defaultValue) {
        try {
            String value =  System.getenv(key);
            return value.isEmpty() ? defaultValue : value;
        } catch (SecurityException | NullPointerException e) {
            return defaultValue;
        }
    }

    public static String calculateHMAC512(String data, String key) {

        String HMAC_SHA512 = "HmacSHA512";
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);
        Mac mac;
        try {
            mac = Mac.getInstance(HMAC_SHA512);
            mac.init(secretKeySpec);
            return Hex.encodeHexString(mac.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("something went wrong while computing hmac hash: {}", e.getLocalizedMessage());
            throw new AppException("something went wrong while computing hmac hmac has",e);
        }
    }

}


