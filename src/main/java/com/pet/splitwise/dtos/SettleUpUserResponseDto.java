package com.pet.splitwise.dtos;

import com.pet.splitwise.strategies.settleupstrategies.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettleUpUserResponseDto extends BaseResponseDto {
    List<Transaction> transactions;
}
