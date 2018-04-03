package com.cathay.kb.practice.session.security;

import com.cathay.kb.practice.session.security.bean.GrantedAuthorityImpl;
import com.cathay.kb.practice.session.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (isValid(authentication)) {
            return getNewAuthentication(authentication);
        }
        throw new BadCredentialsException("password fail");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean isValid(Authentication authentication) {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        return name.equals("admin") && password.equals("123456");
    }

    private Authentication getNewAuthentication(Authentication authentication) {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<GrantedAuthority> rolesList = getRolesByName(name);
        return new UsernamePasswordAuthenticationToken(name, password, rolesList);
    }

    private List<GrantedAuthority> getRolesByName(String name) {
        return authService.findUserRoles(name)
                .stream()
                .map(GrantedAuthorityImpl::new)
                .collect(toList());
    }
}