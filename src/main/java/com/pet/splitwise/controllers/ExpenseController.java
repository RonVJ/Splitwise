package com.pet.splitwise.controllers;

import com.pet.splitwise.dtos.SettleUpGroupRequestDto;
import com.pet.splitwise.dtos.SettleUpGroupResponseDto;
import com.pet.splitwise.dtos.SettleUpUserRequestDto;
import com.pet.splitwise.dtos.SettleUpUserResponseDto;
import com.pet.splitwise.exceptions.GroupDoesNotExist;
import com.pet.splitwise.exceptions.UserDoesNotExistException;
import com.pet.splitwise.services.ExpenseService;
import com.pet.splitwise.strategies.settleupstrategies.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ExpenseController {
    private ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public SettleUpUserResponseDto settleUpUser(SettleUpUserRequestDto request) {
        Long userId = request.getUserId();
        SettleUpUserResponseDto response = new SettleUpUserResponseDto();
        List<Transaction> transactions;
        try {
            transactions = expenseService.settleUpUser(userId);
            response.setTransactions(transactions);
            response.setStatus("SUCCESS");
        } catch (UserDoesNotExistException userDoesNotExistException) {
            response.setStatus("FAILURE");
            response.setMessage(userDoesNotExistException.getMessage());
        }
        return response;
    }

    public SettleUpGroupResponseDto settleUpGroup(SettleUpGroupRequestDto request) {
        Long groupId = request.getGroupId();
        SettleUpGroupResponseDto response = new SettleUpGroupResponseDto();
        List<Transaction> transactions = null;
        try {
            transactions = expenseService.settleUpGroup(groupId);
            response.setTransactions(transactions);
            response.setStatus("SUCCESS");
        } catch (GroupDoesNotExist groupDoesNotExist) {
            response.setStatus("FAILURE");
            response.setMessage(groupDoesNotExist.getMessage());
        }
        return response;
    }
}
