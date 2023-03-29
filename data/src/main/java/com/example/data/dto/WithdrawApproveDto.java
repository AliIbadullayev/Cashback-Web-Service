package com.example.data.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WithdrawApproveDto implements Serializable {
    private Boolean isApproved;

    private String stringIdentifier;
}
