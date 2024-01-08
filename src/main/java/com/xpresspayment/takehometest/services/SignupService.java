
package com.xpresspayment.takehometest.services;

import com.xpresspayment.takehometest.data.UserRepository;
import com.xpresspayment.takehometest.common.dto.account.UserDto;
import com.xpresspayment.takehometest.common.dto.auth.request.SignUpRequest;
import com.xpresspayment.takehometest.common.dto.auth.response.SignUpResponse;
import com.xpresspayment.takehometest.common.enumconstants.HttpStatusCode;
import com.xpresspayment.takehometest.common.exceptions.AppException;
import com.xpresspayment.takehometest.common.utils.GlobalUtils;
import com.xpresspayment.takehometest.security.models.UserPrincipal;
import com.xpresspayment.takehometest.security.services.i.AuthenticationService;
import com.xpresspayment.takehometest.security.services.i.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.xpresspayment.takehometest.common.dto.auth.request.SignUpRequest.toUserEntity;

@Service
@Slf4j
@AllArgsConstructor
public class SignupService {

    private final UserRepository userDao;
//    private final OtpDao otpDao;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
//    private final SignupServiceUtil signupServiceUtil;
    private final IUserService userService;

    @Transactional
    public SignUpResponse registerUser (SignUpRequest request) throws AppException {
        if (userDao.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException("user with this email already exists", HttpStatusCode._400);
        }
        try {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
            UserDto userDto = UserDto.toUserDto(userDao.save(toUserEntity(request, GlobalUtils.generateUuid())));
//            signupServiceUtil.asyncSendSignupOtpEmail(request.getFirst_name(), request.getEmail());
//            signupServiceUtil.asyncSendSignupOtpSms(request.getEmail(), request.getPhone_number());
            UserPrincipal userPrincipal = UserPrincipal.createUser(userDto);
            return SignUpResponse.builder()
                    .email(request.getEmail())
                    .token(authenticationService.createAuthenticationToken(userPrincipal))
                    .build();
        } catch (AppException e) {
            log.error("something went wrong while registering user with email {}",
                    request.getEmail(), e);
            throw new AppException("something went wrong while completing signup");
        }

    }

//
//
//    public void activateAccount(String emailOtp) throws InvalidOtpException {
//        Optional<OtpDto> optional = otpDao.findOtpByCode(emailOtp);
//        if (optional.isEmpty()) {
//            log.error("otp does not exist");
//            throw new InvalidOtpException("otp does not exist, " +
//                    "please request for an email verification otp");
//        }
//        Duration timeElapsed = Duration.between(optional.get().getCreatedAt().toLocalDateTime(), Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime());
//        if(timeElapsed.toMinutes() > 10){
//            throw new RevNorthApiException("Invalid OTP. Kindly request for a valid OTP");
//        }
//        try {
//            userDao.verifyUserEmail(optional.get().getEmail());
//            userDao.activateUserAccount(optional.get().getEmail());
//            otpDao.softDeleteOtp(optional.get().getUuid());
//        }
//        catch (DatabaseHandlerException e) {
//            log.error("something went wrong while verifying and activating user account", e);
//            throw new RevNorthApiException("account could not be verified, please request for a new email verification otp");
//        }
//        log.info("user account with email {} has been activated ", optional.get().getEmail());
//    }
//
//
//    public void sendEmailVerificationOtp (EmailVerificationRequest request) throws RevNorthApiException {
//        UserDto userDto = userDao.findUserByEmail(request.getEmail()).orElseThrow(
//                () ->  new  UserException("user does not exist", HttpStatusCode._400));
//        signupServiceUtil.asyncSendSignupOtpEmail(userDto.getFirst_name(), request.getEmail());
//    }

}
