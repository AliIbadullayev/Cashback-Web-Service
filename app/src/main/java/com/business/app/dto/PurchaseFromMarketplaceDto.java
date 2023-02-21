package com.business.app.dto;

import lombok.Data;

@Data
public class PurchaseFromMarketplaceDto {
    private double cashbackPercent;
    private double totalPrice;
    private long marketplaceId;
    private String username;
}
