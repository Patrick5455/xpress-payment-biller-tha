/*
 * Copyright (c) 2022. Fintellics Technologies Inc and its subsidiaries - All Rights Reserved.
 *  Unauthorized copying of this file and other files within the project, via any medium is strictly prohibited Proprietary and
 *    confidential  Written by Patrick Ojunde <p@revnorth.io>
 */

package com.xpresspayment.takehometest.middleware.security.services.i;

import com.xpresspayment.takehometest.middleware.security.models.AuthOwnerDetails;
import com.xpresspayment.takehometest.middleware.security.models.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    /**
     *
     * @param authentication
     * @return
     * @apiNote  useful for OAuth type of authentication
     */
    String createAuthenticationToken(Authentication authentication);

    /**
     *
     * @param userDetails
     * @return
     */
    String createAuthenticationToken(UserPrincipal userDetails);

    /**
     *
     * @param authToken
     */
    void invalidateAuthenticationToken(String authToken);

    /**
     *
     * @param authToken
     * @return
     */
    boolean isValidAuthenticationToken(String authToken);

    /**
     *
     * @param authToken
     * @return
     */
    AuthOwnerDetails getAuthenticationOwnerDetails(String authToken);




}
