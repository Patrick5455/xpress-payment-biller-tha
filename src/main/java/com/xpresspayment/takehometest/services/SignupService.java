
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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.xpresspayment.takehometest.common.dto.auth.request.SignUpRequest.toUserEntity;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SignupService {

    private final UserRepository userDao;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponse registerUser (SignUpRequest request) throws AppException {
        if (userDao.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException("user with this email already exists", HttpStatusCode.STATUS_400);
        }
        try {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
            UserDto userDto = UserDto.toUserDto(userDao.save(toUserEntity(request, GlobalUtils.generateUuid())));
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

}
