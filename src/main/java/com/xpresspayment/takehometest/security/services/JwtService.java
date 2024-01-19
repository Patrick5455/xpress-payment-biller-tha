
package com.xpresspayment.takehometest.security.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.xpresspayment.takehometest.common.exceptions.InvalidTokenException;
import com.xpresspayment.takehometest.common.utils.GlobalUtils;
import com.xpresspayment.takehometest.common.utils.i.AbstractCachingService;
import com.xpresspayment.takehometest.security.constants.SecurityConstants;
import com.xpresspayment.takehometest.security.models.AuthOwnerDetails;
import com.xpresspayment.takehometest.security.models.UserPrincipal;
import com.xpresspayment.takehometest.security.services.i.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtService extends SecurityConstants implements AuthenticationService {

    private final AbstractCachingService<String, Object> redisCaching;

    public JwtService(@Qualifier("redis") AbstractCachingService<String, Object> redisCaching) {
        this.redisCaching = redisCaching;
    }

    @Override
    public String createAuthenticationToken(Authentication authentication) {
        log.info("creating auth token");
        return createAuthenticationToken(
                (UserPrincipal) authentication.getPrincipal());
    }

    @Override
    public String createAuthenticationToken(UserPrincipal userDetails) {
        Date now = new Date(System.currentTimeMillis());
        String authToken = Jwts.builder()
                .addClaims(userDetails.getAttributes())
                .setSubject(userDetails.getEmail())
                .setHeaderParam("api", "v1")
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .setId(userDetails.getUuid())
                .setExpiration(setExpiration())
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        redisCaching.saveOrUpdate(getClaim(authToken, Claims::getId), authToken);
        return authToken;
    }

    @Override
    public void invalidateAuthenticationToken(String authToken) {
        Claims claims = extractAllClaims(authToken);
        claims.setExpiration(toDate());
        redisCaching.remove(getClaim(authToken, Claims::getId));
        redisCaching.remove(claims.getSubject());
    }

    @Override
    public boolean isValidAuthenticationToken(String authToken) {
        return !isExpired(getAuthenticationOwnerDetails(authToken))
                && isTokenCached(authToken);
    }

    @Override
    public AuthOwnerDetails getAuthenticationOwnerDetails(String authToken) {

        return AuthOwnerDetails.builder()
                .username(getClaim(authToken, Claims::getSubject))
                .authExpiredAt(toLocalDateTime(
                        getClaim(authToken, Claims::getIssuedAt)))
                .authExpiredAt(toLocalDateTime(
                        getClaim(authToken, Claims::getExpiration)))
                .build();
    }

    private static Date setExpiration(){
       return Date.from(
                Timestamp.valueOf(
                        GlobalUtils.calculateFutureDateFromNow(
                                1, TimeUnit.HOURS
                        )).toInstant()
        );
    }

    private static Claims extractAllClaims (String token) throws InvalidTokenException {

        try {
          return  Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token).getBody();
        }
        catch (Exception e) {
            log.error("some issues with passed token", e);
            throw new InvalidTokenException(INVALID_TOKEN_ERROR_MESSAGE,  e);
        }
    }

    private  <T> T getClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private static Date toDate() {
        return Date.from(Timestamp.valueOf(LocalDateTime.now()).toInstant());
    }

    private static boolean isExpired (AuthOwnerDetails authOwnerDetails){
        return LocalDateTime.now().isAfter(
                authOwnerDetails.getAuthExpiredAt());
    }

    private boolean isTokenCached(String authToken){
        if (!redisCaching.contains(getClaim(authToken, Claims::getId)))
            return false;
        String cachedToken = (String) redisCaching.getOrDefault(getClaim(authToken, Claims::getId), ' ');
        return authToken.trim().equalsIgnoreCase(cachedToken.trim());
    }

}
