package com.pet.splitwise.services;

import com.pet.splitwise.exceptions.GroupDoesNotExist;
import com.pet.splitwise.exceptions.UserDoesNotExistException;
import com.pet.splitwise.models.Expense;
import com.pet.splitwise.models.Group;
import com.pet.splitwise.models.User;
import com.pet.splitwise.models.UserExpense;
import com.pet.splitwise.repositories.ExpenseRepository;
import com.pet.splitwise.repositories.GroupRepository;
import com.pet.splitwise.repositories.UserExpenseRepository;
import com.pet.splitwise.repositories.UserRepository;
import com.pet.splitwise.strategies.settleupstrategies.SettleUpStrategy;
import com.pet.splitwise.strategies.settleupstrategies.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private UserRepository userRepository;
    private UserExpenseRepository userExpenseRepository;
    private SettleUpStrategy settleUpStrategy;
    private ExpenseRepository expenseRepository;
    private GroupRepository groupRepository;

    @Autowired
    public ExpenseService(UserExpenseRepository userExpenseRepository
    , UserRepository userRepository
    , @Qualifier("twoSetsSettleUpStrategy") SettleUpStrategy settleUpStrategy
    , ExpenseRepository expenseRepository
    , GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.userExpenseRepository = userExpenseRepository;
        this.settleUpStrategy = settleUpStrategy;
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
    }
    public List<Transaction> settleUpUser(Long userId) throws UserDoesNotExistException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserDoesNotExistException();
        }

        User user = userOptional.get();
        List<Expense> expensesInvolvingUser = new ArrayList<>();
        List<UserExpense> userExpenses;
        userExpenses = userExpenseRepository.findAllByUser(user);

        for(UserExpense userExpense : userExpenses) {
            expensesInvolvingUser.add(userExpense.getExpense());
        }

        List<Transaction> transactions = settleUpStrategy.settleUp(expensesInvolvingUser);

        List<Transaction> filteredTransactions = new ArrayList<>();
        for(Transaction transaction : transactions) {
            if(transaction.getTo().equals(user)
                    || transaction.getFrom().equals(user)) {
                filteredTransactions.add(transaction);
            }
        }

        return filteredTransactions;
    }

    public List<Transaction> settleUpGroup(Long groupId) throws GroupDoesNotExist {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isEmpty()) {
            throw new GroupDoesNotExist();
        }
        Group group = groupOptional.get();
        List<Expense> expenses = expenseRepository.findAllByGroup(group);
        List<Transaction> transactions = settleUpStrategy.settleUp(expenses);

        return transactions;
    }
}
