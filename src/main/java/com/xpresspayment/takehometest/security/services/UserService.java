

package com.xpresspayment.takehometest.middleware.security.services;


import com.xpresspayment.takehometest.commons.dto.account.UserDto;
import com.xpresspayment.takehometest.commons.exceptions.InvalidTokenException;
import com.xpresspayment.takehometest.commons.utils.i.CachingService;
import com.xpresspayment.takehometest.data.UserRepository;
import com.xpresspayment.takehometest.middleware.security.models.UserPrincipal;
import com.xpresspayment.takehometest.middleware.security.services.i.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserService implements IUserService {

    private final UserRepository userDao;
    private final CachingService<String, Object> redisCaching;

    @Autowired
    public UserService(UserRepository userDao,
                       @Qualifier("redis") CachingService<String, Object> redisCaching) {
        this.userDao = userDao;
        this.redisCaching = redisCaching;
    }

    @Override
    public UserDto loadUserDtoByUsername(String email) throws UsernameNotFoundException{
        return  UserDto.toUserDto(userDao.findUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("the email does not exist")
        ));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto userDto =
                UserDto.toUserDto(userDao.findUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("the email does not exist")
        ));

        return UserPrincipal.createUser(userDto);
    }

    @Override
    public UserDto getLoggedInUser() throws InvalidTokenException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto;
        userDto = (UserDto) redisCaching.getOrDefault(
                authentication.getName(), null, UserDto.class);
        if (userDto == null) {
            return UserDto.toUserDto(userDao.findUserByEmail(authentication.getName())
                    .orElseThrow(() -> new InvalidTokenException("you are logged out, please login again")));
        }
        return userDto;
    }

}
