package com.pet.splitwise.strategies.settleupstrategies;

import com.pet.splitwise.models.Expense;
import com.pet.splitwise.strategies.settleupstrategies.Transaction;

import java.util.List;

public interface SettleUpStrategy {
    List<Transaction> settleUp(List<Expense> expenses);
}
