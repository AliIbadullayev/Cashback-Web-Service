package com.example.transaction_service.dto;

import lombok.Data;

@Data
public class PurchaseApproveDto {
    Boolean isApproved;
    Long marketplaceId;
}
