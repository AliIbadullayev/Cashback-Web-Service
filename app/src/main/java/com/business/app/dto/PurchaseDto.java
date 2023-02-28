package com.business.app.dto;

import com.business.app.model.Purchase;
import com.business.app.model.Status;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PurchaseDto {

    private Long id;
    private String marketplaceName;
    private Status cashbackPaymentStatus;
    private boolean rulesRespected;
    private Timestamp timestamp;
    private double cashbackPercent;
    private double totalPrice;

    public static PurchaseDto fromPurchase(Purchase purchase) {
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setId(purchase.getId());
        purchaseDto.setTimestamp(purchase.getTimestamp());
        purchaseDto.setCashbackPercent(purchase.getCashbackPercent());
        purchaseDto.setRulesRespected(purchase.isRulesRespected());
        purchaseDto.setTotalPrice(purchase.getTotalPrice());
        purchaseDto.setCashbackPaymentStatus(purchase.getCashbackPaymentStatus());

        String marketplaceName = purchase.getMarketplace() != null ? purchase.getMarketplace().getName() : "undefined";
        purchaseDto.setMarketplaceName(marketplaceName);

        return purchaseDto;
    }

}
