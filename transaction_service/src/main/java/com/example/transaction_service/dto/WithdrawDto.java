package com.example.transaction_service.dto;

import lombok.Data;

@Data
public class WithdrawDto {
    double amount;
    long paymentMethodId;
    String username;
    String credential;
}
