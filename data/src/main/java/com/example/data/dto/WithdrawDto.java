package com.example.data.dto;

import com.example.data.model.Status;
import lombok.Data;

import java.io.Serializable;

@Data
public class WithdrawDto implements Serializable {
    double amount;
    long paymentMethodId;
    String username;
    String credential;

    private Status withdrawStatus;

    private String stringIdentifier;

    private String errorMessage;


}
