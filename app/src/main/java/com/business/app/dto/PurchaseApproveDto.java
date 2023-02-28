package com.business.app.dto;

import lombok.Data;

@Data
public class PurchaseApproveDto {
    Boolean isApproved;
    Long marketplaceId;
}
