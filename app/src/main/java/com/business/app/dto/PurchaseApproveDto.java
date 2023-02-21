package com.business.app.dto;

import lombok.Data;

@Data
public class PurchaseApproveDto {
    Long purchaseId;
    Boolean isApproved;
    Long marketplaceId;
}
