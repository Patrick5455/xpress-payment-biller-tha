package com.xpresspayment.takehometest.unittest.common.utils;

import static org.junit.jupiter.api.Assertions.*;

import com.xpresspayment.takehometest.AbstractTest;
import com.xpresspayment.takehometest.common.exceptions.InvalidUuidException;
import com.xpresspayment.takehometest.common.exceptions.UnsupportedTimeUnitException;
import com.xpresspayment.takehometest.common.utils.GlobalUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;



class GlobalUtilsTest extends AbstractTest {

    @Test
    void testGenerateUuid() {
        String uuid = GlobalUtils.generateUuid();
        assertNotNull(uuid, "UUID should not be null");
        assertEquals(16, uuid.length(), "UUID length should be 16");
    }

    @Test
    void testValidateEmail() {
        assertTrue(GlobalUtils.validateEmail("test@example.com"), "Valid email should return true");
        assertFalse(GlobalUtils.validateEmail("invalid-email"), "Invalid email should return false");
    }

    @Test
    void testValidatePhoneNumber() {
        assertTrue(GlobalUtils.validatePhoneNumber("+2341234567890"), "Valid phone number should return true");
        assertFalse(GlobalUtils.validatePhoneNumber("invalid-phone-number"), "Invalid phone number should return false");
    }

    @Test
    void testGenerateToken() {
        String token = GlobalUtils.generateToken(10);
        assertNotNull(token, "Token should not be null");
        assertEquals(10, token.length(), "Token length should be 10");
    }

    @Test
    void testGenerateOtp() {
        String otp = GlobalUtils.generateOtp(6);
        assertNotNull(otp, "OTP should not be null");
        assertEquals(6, otp.length(), "OTP length should be 6");
    }

    @Test
    void testValidateUuid() throws InvalidUuidException {
        String validUuid = "550e8400-e29b-41d4-a716-446655440000";
        assertEquals(validUuid, GlobalUtils.validateUuid(validUuid), "Valid UUID should return the same UUID");

        String invalidUuid = "invalid-uuid";
        assertThrows(InvalidUuidException.class, () -> GlobalUtils.validateUuid(invalidUuid), "Invalid UUID should throw InvalidUuidException");
    }

    @Test
    void testCalculateFutureDateFromNow() throws UnsupportedTimeUnitException {
        LocalDateTime futureDate = GlobalUtils.calculateFutureDateFromNow(1, TimeUnit.DAYS);
        assertNotNull(futureDate, "Future date should not be null");
    }

    @Test
    void testGetOrDefault() {
        String defaultValue = "default";
        assertEquals("test-value", GlobalUtils.getOrDefault("TEST_KEY", "test-value"), "Should return the value of the key if it exists");
        assertEquals(defaultValue, GlobalUtils.getOrDefault("NON_EXISTENT_KEY", defaultValue), "Should return the default value if the key does not exist");
    }

}
