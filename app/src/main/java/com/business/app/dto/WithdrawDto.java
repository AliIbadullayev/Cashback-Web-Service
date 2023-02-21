package com.business.app.dto;

import lombok.Data;

@Data
public class WithdrawDto {
    double amount;
    long paymentMethodId;
    String username;
    String credential;
}
