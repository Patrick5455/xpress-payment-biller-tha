/*
 * Copyright (c) 2022. Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 *  Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 *    confidential  Written by Patrick Ojunde <p@revnorth.io>
 */

package com.xpresspayment.takehometest.middleware.security.services.i;

import com.xpresspayment.takehometest.commons.dto.account.UserDto;
import com.xpresspayment.takehometest.commons.exceptions.AppException;
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
