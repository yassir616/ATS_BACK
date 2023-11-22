package com.soa.vie.takaful.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soa.vie.takaful.SpringApplicationContext;
import com.soa.vie.takaful.entitymodels.TakafulUser;
import com.soa.vie.takaful.requestmodels.SingingUpUserRequestModel;
import com.soa.vie.takaful.services.IUserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            SingingUpUserRequestModel UserInfo = new ObjectMapper().readValue(request.getInputStream(),
                    SingingUpUserRequestModel.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(UserInfo.getEmail(),
                    UserInfo.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String userName = ((User) authResult.getPrincipal()).getUsername();

        IUserService userService = (IUserService) SpringApplicationContext.getBean("userServiceImpl");
        TakafulUser user = userService.getUser(userName);

        String token = Jwts.builder().setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityCanstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityCanstants.getSecretToken()).compact();

        response.addHeader(SecurityCanstants.HEADER_STRING, SecurityCanstants.TOKEN_PREFIX + token);
        response.addHeader("UserID", user.getId());
    }
}
