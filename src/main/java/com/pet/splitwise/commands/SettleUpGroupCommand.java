package com.pet.splitwise.commands;

import com.pet.splitwise.controllers.ExpenseController;
import com.pet.splitwise.dtos.SettleUpGroupRequestDto;
import com.pet.splitwise.dtos.SettleUpGroupResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SettleUpGroupCommand implements Command{
    private ExpenseController expenseController;

    @Autowired
    public SettleUpGroupCommand(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();

        if(inputWords.size() == 3 && inputWords.get(1).equalsIgnoreCase(CommandKeywords.SETTLE_UP_GROUP) ) {
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();

        Long groupId = Long.parseLong(inputWords.get(2));

        SettleUpGroupRequestDto request = new SettleUpGroupRequestDto();
        request.setGroupId(groupId);

        // call user controller
        SettleUpGroupResponseDto response = expenseController.settleUpGroup(request);
    }
}
