package com.business.app.dto;

import lombok.Data;

@Data
public class WithdrawApproveDto {
    Long withdrawId;
    Boolean isApproved;
}
