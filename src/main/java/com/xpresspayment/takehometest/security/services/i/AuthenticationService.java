

package com.xpresspayment.takehometest.security.services.i;

import com.xpresspayment.takehometest.security.models.AuthOwnerDetails;
import com.xpresspayment.takehometest.security.models.UserPrincipal;
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
