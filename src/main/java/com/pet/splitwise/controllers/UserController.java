package com.pet.splitwise.controllers;

import com.pet.splitwise.dtos.RegisterUserRequestDto;
import com.pet.splitwise.dtos.RegisterUserResponseDto;
import com.pet.splitwise.exceptions.UserAlreadyExistsException;
import com.pet.splitwise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.pet.splitwise.models.User;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public RegisterUserResponseDto registerUser(RegisterUserRequestDto request) {
        User user;
        RegisterUserResponseDto response = new RegisterUserResponseDto();

        String phoneNumber = request.getPhoneNumber();
        String userName = request.getUserName();
        String password = request.getPassword();

        try {
            user = userService.registerUser(userName
                    , phoneNumber
                    , password);


            response.setUserId(user.getId());
            response.setStatus("SUCCESS");
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            response.setStatus("FAILURE");
            response.setMessage(userAlreadyExistsException.getMessage());
        }

        return response;
    }
}
