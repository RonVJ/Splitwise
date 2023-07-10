package com.pet.splitwise.commands;

import com.pet.splitwise.controllers.UserController;
import com.pet.splitwise.dtos.RegisterUserRequestDto;
import com.pet.splitwise.dtos.RegisterUserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RegisterUserCommand implements Command{
//    Register amit 8847522478 pass123
    private UserController userController;

    @Autowired
    public RegisterUserCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();

        if(inputWords.size() == 4 && inputWords.get(0).equalsIgnoreCase(CommandKeywords.REGISTER_USER) ) {
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();

        String userName = inputWords.get(1);
        String phoneNumber = inputWords.get(2);
        String password = inputWords.get(3);

        RegisterUserRequestDto request = new RegisterUserRequestDto();


        request.setUserName(userName);
        request.setPassword(password);
        request.setPhoneNumber(phoneNumber);

        // call user controller
        RegisterUserResponseDto response = userController.registerUser(request);

    }
}
