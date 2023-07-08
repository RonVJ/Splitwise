package com.pet.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Entity
public class Expense extends BaseModel{
    @ManyToOne
    private Group group;

    private Long amount;
    private String description;

    @ManyToOne
    private User addedBy;

    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;

}
