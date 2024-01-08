

package com.xpresspayment.takehometest.security.services.i;

import com.xpresspayment.takehometest.common.dto.account.UserDto;
import com.xpresspayment.takehometest.common.exceptions.AppException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface IUserService extends UserDetailsService {


    /**
     *
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    UserDto loadUserDtoByUsername(String email) throws UsernameNotFoundException;

    /**
     *
     * @return
     * @throws AppException
     */
    UserDto getLoggedInUser() throws AppException;

}
