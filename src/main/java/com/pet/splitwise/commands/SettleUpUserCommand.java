package com.pet.splitwise.commands;

import com.pet.splitwise.controllers.ExpenseController;
import com.pet.splitwise.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SettleUpUserCommand implements Command{
    private ExpenseController expenseController;

    @Autowired
    public SettleUpUserCommand(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();

        if(inputWords.size() == 2 && inputWords.get(1).equalsIgnoreCase(CommandKeywords.SETTLE_UP_USER) ) {
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();

        Long userId = Long.parseLong(inputWords.get(0));

        SettleUpUserRequestDto request = new SettleUpUserRequestDto();
        request.setUserId(userId);

        // call user controller
        SettleUpUserResponseDto response = expenseController.settleUpUser(request);
    }
}
