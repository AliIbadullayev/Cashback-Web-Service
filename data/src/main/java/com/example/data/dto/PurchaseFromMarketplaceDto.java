package com.example.data.dto;

import lombok.Data;

@Data
public class PurchaseFromMarketplaceDto {
    private double cashbackPercent;
    private double totalPrice;
    private long marketplaceId;
    private String username;

    private String stringIdentifier;

}
