package com.example.icommerce.controllers;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.icommerce.constants.Constants;
import com.example.icommerce.constants.ErrorCode;
import com.example.icommerce.dtos.UserActivityDTO;
import com.example.icommerce.entities.User;
import com.example.icommerce.models.ObjectError;
import com.example.icommerce.models.Response;
import com.example.icommerce.models.UserRequestModel;
import com.example.icommerce.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Response<User> createUser(@RequestBody UserRequestModel model) {
        User user = userService.createUser(model);

        if ( user != null ) {
            return new Response<>(user);
        }

        return new Response<>(new ObjectError(ErrorCode.SERVER_ERROR, "SERVER ERROR"));
    }

    @GetMapping("/activities")
    public Response<?> getActivities(
        @Valid @NotEmpty(message = "identityId is empty") @RequestHeader(Constants.REQUEST_IDENTITY_ID_HEADER) String identityId) {
        User user = userService.getUserByUsername(identityId);
        if( Objects.isNull(user)) {
            return new Response<>(new ObjectError(ErrorCode.INVALID_REQUEST, "User '" + identityId + "' not found"));
        }

        List<UserActivityDTO> activities = userService.getAllActivities(identityId);

        return new Response<>(activities);
    }


}
