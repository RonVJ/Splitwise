package com.pet.splitwise.commands;

import com.pet.splitwise.controllers.UserController;
import com.pet.splitwise.dtos.RegisterUserRequestDto;
import com.pet.splitwise.dtos.RegisterUserResponseDto;
import com.pet.splitwise.dtos.UpdateProfileRequestDto;
import com.pet.splitwise.dtos.UpdateProfileResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UpdateProfileCommand implements Command{
    //  u1 UpdateProfile robinchwan
    private UserController userController;

    @Autowired
    public UpdateProfileCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();

        if(inputWords.size() == 3 && inputWords.get(1).equalsIgnoreCase(CommandKeywords.UPDATE_PROFILE) ) {
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();

        String password = inputWords.get(2);
        String userName = inputWords.get(0);

        UpdateProfileRequestDto request = new UpdateProfileRequestDto();


        request.setUserName(userName);
        request.setPassword(password);

        // call user controller
        UpdateProfileResponseDto response = userController.updateProfile(request);

    }
}
