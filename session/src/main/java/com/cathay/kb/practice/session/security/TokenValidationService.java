package com.cathay.kb.practice.session.security;


import com.cathay.kb.practice.session.service.AuthService;
import com.cathay.kb.practice.session.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

@Component
public class TokenValidationService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    public String getJwt(Authentication authentication) {

        String name = authentication.getName();
        String roles = authService.findUserRoles(name).stream().collect(joining(","));
        return jwtService.getJwtStr(name, roles);
    }

    public Authentication getAuthentication(String jwtStr) {
        Claims claims = jwtService.getJwtClaims(jwtStr);
        String name = claims.getSubject();
        List<GrantedAuthority> authorities =
                Optional.ofNullable(AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get(jwtService.getROLE_KEY())))
                        .orElseGet(ArrayList::new);

        return name != null ?
                new UsernamePasswordAuthenticationToken(name, null, authorities) : null;
    }
}