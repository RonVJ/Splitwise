package com.pet.splitwise.repositories;

import com.pet.splitwise.models.Expense;
import com.pet.splitwise.models.User;
import com.pet.splitwise.models.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense, Long> {
    List<UserExpense> findAllByUser(User user);

    List<UserExpense> findAllByExpenseIn(List<Expense> expenses);

}
