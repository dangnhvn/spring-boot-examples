package com.example.icommerce.configurations.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.icommerce.constants.Constants;
import com.example.icommerce.controllers.RestExceptionHandler;
import com.example.icommerce.exceptions.AuthenticationException;
import com.example.icommerce.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final RestExceptionHandler    restExceptionHandler;
    private final List<String> allowedEndpoints;
    private final UserService  userService;

    @Autowired
    public AuthenticationFilter (RestExceptionHandler restExceptionHandler, UserService userService) {
        this.restExceptionHandler = restExceptionHandler;
        this.userService = userService;
        allowedEndpoints = Arrays.asList("/h2", "/api/health", "/api/users");
    }


    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        if ( allowedEndpoints.stream().noneMatch(endpoint -> request.getRequestURI().contains(endpoint)) &&
             StringHelper.isEmpty(request.getHeader(Constants.REQUEST_IDENTITY_ID_HEADER)) ) {
            writeErrorResponse(response, "Must pass valid identity ID");
            return;
        }

        String requestId = request.getHeader(Constants.REQUEST_ID_HEADER);
        if ( StringHelper.isEmpty(requestId) ) {
            requestId = UUID.randomUUID().toString();
        }

        response.addHeader(
            Constants.REQUEST_ID_HEADER,
            URLEncoder.encode(requestId, StandardCharsets.UTF_8.displayName())
        );

        String identityId = request.getHeader(Constants.REQUEST_IDENTITY_ID_HEADER);
        String api = request.getRequestURI();
        String method = request.getMethod();
        String queryString = request.getQueryString();

        if(StringHelper.isNotEmpty(identityId)) {
            userService.createCustomerActivity(identityId, api, method, queryString);
        }

        filterChain.doFilter(request, response);
    }

    private void writeErrorResponse (HttpServletResponse response, String message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(
            restExceptionHandler.handleAuthenticationException(new AuthenticationException(message))));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
