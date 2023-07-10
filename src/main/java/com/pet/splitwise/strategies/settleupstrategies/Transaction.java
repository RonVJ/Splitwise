package com.pet.splitwise.strategies.settleupstrategies;

import com.pet.splitwise.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private User to;
    private User from;
    private Long amount;
}
