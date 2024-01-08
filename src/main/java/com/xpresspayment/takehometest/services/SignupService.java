/*
 * Copyright (c) 2021-2022. Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 *  Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 *    confidential  Written by Patrick Ojunde <p@revnorth.io>
 */
package io.revnorth.account.services.onboarding;

import static io.revnorth.webdtos.request.auth.SignUpRequest.toUserEntity;
import static java.time.temporal.ChronoUnit.DAYS;
import io.revnorth.account.utils.SignupServiceUtil;
import io.revnorth.common.webdtos.request.account.settings.EmailVerificationRequest;
import io.revnorth.common.webdtos.response.account.onboarding.AccountInviteDetailsResponse;
import io.revnorth.dao.account.interfaces.AccountInviteDao;
import io.revnorth.dao.account.interfaces.BetaUserDao;
import io.revnorth.dao.account.interfaces.OtpDao;
import io.revnorth.dao.account.interfaces.UserDao;
import io.revnorth.domain.entity.account.BetaUserEntity;
import io.revnorth.dto.account.AccountInviteDto;
import io.revnorth.dto.account.BetaUserDto;
import io.revnorth.dto.account.OtpDto;
import io.revnorth.dto.account.UserDto;
import io.revnorth.enumconstant.HttpStatusCode;
import io.revnorth.enumconstant.UserType;
import io.revnorth.exception.DatabaseHandlerException;
import io.revnorth.exception.InvalidOtpException;
import io.revnorth.exception.RevNorthApiException;
import io.revnorth.exception.UserException;
import io.revnorth.security.models.UserPrincipal;
import io.revnorth.security.services.i.AuthenticationService;
import io.revnorth.security.services.i.IUserService;
import io.revnorth.utils.GlobalUtils;
import io.revnorth.webdtos.request.auth.SignUpRequest;
import io.revnorth.webdtos.response.auth.SignUpResponse;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SignupService {

    private final UserDao userDao;
    private final BetaUserDao betaUserDao;
    private final OtpDao otpDao;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    private final SignupServiceUtil signupServiceUtil;
    private final IUserService userService;
    private final AccountInviteDao accountInviteDao;

    public SignUpResponse registerUser (SignUpRequest request) throws UserException {
        if (userDao.findUserByEmail(request.getEmail()).isPresent()) {
            throw new UserException("user with this email already exists", HttpStatusCode._400);
        }
        if ( userDao.findUserByPhone(request.getPhone_number()).isPresent()) {
            throw new UserException("user with this phone already exists", HttpStatusCode._400);
        }
        //todo validate investor user was invited
        try {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
            UserDto userDto = userDao.insertUser(toUserEntity(request));
            signupServiceUtil.asyncSetUpAccountTypeOnSignup(UserType.valueOf(request.getUser_type()), userDto);
            signupServiceUtil.asyncSendSignupOtpEmail(request.getFirst_name(), request.getEmail());
            signupServiceUtil.asyncSendSignupOtpSms(request.getEmail(), request.getPhone_number());
            UserPrincipal userPrincipal = UserPrincipal.createUser(userDto);
            return SignUpResponse.builder()
                    .account_type(UserType.valueOf(request.getUser_type()).name())
                    .email(request.getEmail())
                    .token(authenticationService.createAuthenticationToken(userPrincipal))
                    .build();
        } catch (RevNorthApiException e) {
            log.error("something went wrong while registering user with email {}",
                    request.getEmail(), e);
            throw new RevNorthApiException("something went wrong while completing signup");
        }

    }


    public void activateAccount(String emailOtp) throws InvalidOtpException {
        Optional<OtpDto> optional = otpDao.findOtpByCode(emailOtp);
        if (optional.isEmpty()) {
            log.error("otp does not exist");
            throw new InvalidOtpException("otp does not exist, " +
                    "please request for an email verification otp");
        }
        Duration timeElapsed = Duration.between(optional.get().getCreatedAt().toLocalDateTime(), Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime());
        if(timeElapsed.toMinutes() > 10){
            throw new RevNorthApiException("Invalid OTP. Kindly request for a valid OTP");
        }
        try {
            userDao.verifyUserEmail(optional.get().getEmail());
            userDao.activateUserAccount(optional.get().getEmail());
            otpDao.softDeleteOtp(optional.get().getUuid());
        }
        catch (DatabaseHandlerException e) {
            log.error("something went wrong while verifying and activating user account", e);
            throw new RevNorthApiException("account could not be verified, please request for a new email verification otp");
        }
        log.info("user account with email {} has been activated ", optional.get().getEmail());
    }

    public void verifyPhone(String phoneOtp) throws InvalidOtpException {
        Optional<OtpDto> optional = otpDao.findOtpByCode(phoneOtp);
        if (optional.isEmpty()) {
            log.error("otp does not exist");
            throw new InvalidOtpException("otp does not exist, " +
                    "please request for a phone verification otp");
        }
        try {
           UserDto userDto = userDao.findUserByEmail(optional.get().getEmail()).orElseThrow(
                    () ->  new  UserException("user does not exist", HttpStatusCode._400));
            if (userDto.getPrimary_phone() == null) {
                throw new RevNorthApiException("no phone number has been provided by user," +
                        " please provide one");
            }
            userDao.verifyUserPhone(userDto.getPrimary_phone());
            otpDao.softDeleteOtp(optional.get().getUuid());
        }
        catch (DatabaseHandlerException e) {
            log.error("something went wrong while verifying and activating user account", e);
            throw new RevNorthApiException("account could not be verified, " +
                    "please request for a new phone verification otp");
        }
        log.info("user account with email {} has been activated ", optional.get().getEmail());
    }


    public void sendPhoneVerificationOtp() throws RevNorthApiException {
        UserDto userDto = userDao.findUserByPhone(userService.getLoggedInUser().getPrimary_phone()).orElseThrow(
                () ->  new  UserException("user does not exist", HttpStatusCode._400));
        signupServiceUtil.asyncSendSignupOtpSms(userDto.getEmail(), userDto.getPrimary_phone());
    }


    public void sendEmailVerificationOtp (EmailVerificationRequest request) throws RevNorthApiException {
        UserDto userDto = userDao.findUserByEmail(request.getEmail()).orElseThrow(
                () ->  new  UserException("user does not exist", HttpStatusCode._400));
        signupServiceUtil.asyncSendSignupOtpEmail(userDto.getFirst_name(), request.getEmail());
    }

    public BetaUserDto betaNotification(String email) {
        //TODO: send email notification of app launch
       return betaUserDao.insertBetaUser(
                BetaUserEntity.builder()
                        .email(email)
                        .firstName("")
                        .lastName("")
                        .uuid(GlobalUtils.generateUuid())
                        .build()
        );
    }

    public AccountInviteDetailsResponse fetchInviteDetails(String inviteUuid){
        Optional<AccountInviteDto> optional =
                accountInviteDao.findAccountInviteByUuid(inviteUuid);
        if (optional.isEmpty()) {
            throw new RevNorthApiException("Invalid invite");
        }
        AccountInviteDto accountInviteDto = optional.get();
        if (DAYS.between(accountInviteDto.getCreated_at().toLocalDateTime(), LocalDateTime.now()) > 7) {
            throw new RevNorthApiException("Invite has expired. Kindly request for a new invite link");
        }
        return AccountInviteDetailsResponse.builder()
                .firstname(accountInviteDto.getFirst_name())
                .lastname(accountInviteDto.getLast_name())
                .email(accountInviteDto.getEmail())
                .phone(accountInviteDto.getPhone())
                .user_type(accountInviteDto.getUser_type())
                .build();
    }



}
