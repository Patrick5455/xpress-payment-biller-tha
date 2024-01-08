

package com.xpresspayment.takehometest.middleware.security.models;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.xpresspayment.takehometest.commons.dto.account.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static com.xpresspayment.takehometest.middleware.security.models.AttributeValue.AUTHORITY;
import static com.xpresspayment.takehometest.middleware.security.models.AttributeValue.EMAIL;
import static com.xpresspayment.takehometest.middleware.security.models.AttributeValue.ROLE;


@Builder
@AllArgsConstructor
@With
@Slf4j
public class UserPrincipal implements UserDetails {

    @Getter
    private final String uuid;
    @Getter
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> grantedAuthorities;
    @Getter
    private final Map<String, Object> attributes;
    private final boolean isEnabled;


    public static UserPrincipal createUser(UserDto userDto) {
        Collection<? extends GrantedAuthority> authorities =
                Collections.singletonList(new RevnorthGrantedAuthority(userDto.getRole()));
        return UserPrincipal.builder()
                .uuid(userDto.getUuid())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .grantedAuthorities(authorities)
                .isEnabled(userDto.isActive())
                .attributes(buildAttributes(userDto))
                .build();
    }

    private static Map<String, Object> buildAttributes(UserDto userDto) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(EMAIL.name(), userDto.getEmail());
        attributes.put(ROLE.name(), userDto.getRole());
        attributes.put(AUTHORITY.name(), userDto.getRole().getAuthority());
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {
        return true;
    }

}
