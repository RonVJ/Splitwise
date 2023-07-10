package com.pet.splitwise.strategies.settleupstrategies;

import com.pet.splitwise.models.Expense;
import com.pet.splitwise.models.User;
import com.pet.splitwise.models.UserExpense;
import com.pet.splitwise.models.UserExpenseType;
import com.pet.splitwise.repositories.UserExpenseRepository;
import com.pet.splitwise.strategies.settleupstrategies.Transaction;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("twoSetsSettleUpStrategy")
public class TwoSetsSettleUpStrategy implements SettleUpStrategy{
    private UserExpenseRepository userExpenseRepository;

    @Autowired
    public TwoSetsSettleUpStrategy(UserExpenseRepository userExpenseRepository) {
        this.userExpenseRepository = userExpenseRepository;
    }

    @Override
    public List<Transaction> settleUp(List<Expense> expenses) {
        List<UserExpense> allUserExpenseForTheseExpenses = userExpenseRepository.findAllByExpenseIn(expenses);
        Map<User, Long> moneyPaidExtra = new HashMap<>();

        for(UserExpense userExpense : allUserExpenseForTheseExpenses) {
            User user = userExpense.getUser();
            if(!moneyPaidExtra.containsKey(user)) {
                moneyPaidExtra.put(user, 0L);
            }

            if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID)) {
                moneyPaidExtra.put(user, moneyPaidExtra.get(user) + userExpense.getAmount());
            }
            else if(userExpense.getUserExpenseType().equals(UserExpenseType.HAD_TO_PAY)) {
                moneyPaidExtra.put(user, moneyPaidExtra.get(user) - userExpense.getAmount());
            }
        }

        TreeSet<Pair<User, Long>> extraPaid = new TreeSet<>();
        TreeSet<Pair<User, Long>> lessPaid = new TreeSet<>();

        for (Map.Entry<User, Long> userAmount : moneyPaidExtra.entrySet()) {
            if(userAmount.getValue() >= 0) {
                extraPaid.add(new Pair<>(userAmount.getKey(), Math.abs(userAmount.getValue())));
            }
            else {
                lessPaid.add(new Pair<>(userAmount.getKey(), Math.abs(userAmount.getValue())));
            }
        }

        List<Transaction> transactions = new ArrayList<>();
        while(!lessPaid.isEmpty()) {
            Pair<User, Long> lessPaidPair =  lessPaid.pollFirst();
            Pair<User, Long> extraPaidPair =  extraPaid.pollFirst();
            Transaction transaction = new Transaction();
            transaction.setTo(extraPaidPair.a);
            transaction.setFrom(lessPaidPair.a);
            transaction.setAmount(Math.abs(extraPaidPair.b - lessPaidPair.b));

            if(lessPaidPair.b < extraPaidPair.b) {
                extraPaid.add(new Pair<>(extraPaidPair.a, extraPaidPair.b - lessPaidPair.b));
            }
            else if(lessPaidPair.b > extraPaidPair.b) {
                lessPaid.add(new Pair<>(lessPaidPair.a, lessPaidPair.b - extraPaidPair.b));
            }
            transactions.add(transaction);
        }

        return transactions;
    }
}
