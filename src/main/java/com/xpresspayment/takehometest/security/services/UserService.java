

package com.xpresspayment.takehometest.security.services;


import com.xpresspayment.takehometest.common.dto.account.UserDto;
import com.xpresspayment.takehometest.common.exceptions.InvalidTokenException;
import com.xpresspayment.takehometest.common.utils.i.AbstractCachingService;
import com.xpresspayment.takehometest.data.UserRepository;
import com.xpresspayment.takehometest.security.models.UserPrincipal;
import com.xpresspayment.takehometest.security.services.i.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class UserService implements IUserService {

    private final UserRepository userDao;
    private final AbstractCachingService<String, Object> redisCaching;

    @Autowired
    public UserService(UserRepository userDao,
                       @Qualifier("redis") AbstractCachingService<String, Object> redisCaching) {
        this.userDao = userDao;
        this.redisCaching = redisCaching;
    }

    @Override
    public UserDto loadUserDtoByUsername(String email) throws UsernameNotFoundException{
        log.info("loading user dto by email: {}", email);
        return  UserDto.toUserDto(userDao.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("the email does not exist")
        ));
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto userDto =
                UserDto.toUserDto(userDao.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("the email does not exist")
        ));

        return UserPrincipal.createUser(userDto);
    }

    @Override
    public UserDto getLoggedInUser() throws InvalidTokenException {
        Authentication authentication = getAuthentication();
        UserDto userDto;
        log.info("getting logged in user: {}", authentication.getName());
        userDto = (UserDto) redisCaching.getOrDefault(
                authentication.getName(), null, UserDto.class);
        if (userDto == null) {
            return UserDto.toUserDto(userDao.findByEmail(authentication.getName())
                    .orElseThrow(() -> new InvalidTokenException("you are logged out, please login again")));
        }
        return userDto;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
