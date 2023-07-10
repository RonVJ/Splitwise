package com.pet.splitwise.controllers;

import com.pet.splitwise.dtos.RegisterUserRequestDto;
import com.pet.splitwise.dtos.RegisterUserResponseDto;
import com.pet.splitwise.dtos.UpdateProfileRequestDto;
import com.pet.splitwise.dtos.UpdateProfileResponseDto;
import com.pet.splitwise.exceptions.UserAlreadyExistsException;
import com.pet.splitwise.exceptions.UserDoesNotExistException;
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

    public UpdateProfileResponseDto updateProfile(UpdateProfileRequestDto request) {
        User user;
        UpdateProfileResponseDto response = new UpdateProfileResponseDto();

        String userName = request.getUserName();
        String password = request.getPassword();

        try {
            user = userService.updateProfile(userName
                    , password);


            response.setUserId(user.getId());
            response.setStatus("SUCCESS");
        } catch (UserDoesNotExistException userDoesNotExistException) {
            System.out.println("UserDoesNotExistException");
            response.setStatus("FAILURE");
            response.setMessage(userDoesNotExistException.getMessage());
        }

        return response;
    }
}
