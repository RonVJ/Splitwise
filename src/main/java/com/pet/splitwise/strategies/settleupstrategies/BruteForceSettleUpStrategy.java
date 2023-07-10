package com.pet.splitwise.strategies.settleupstrategies;

import com.pet.splitwise.models.Expense;
import org.springframework.stereotype.Component;
import com.pet.splitwise.strategies.settleupstrategies.Transaction;

import java.util.List;

@Component
public class BruteForceSettleUpStrategy implements SettleUpStrategy{
    @Override
    public List<Transaction> settleUp(List<Expense> expenses) {
        return null;
    }
}
