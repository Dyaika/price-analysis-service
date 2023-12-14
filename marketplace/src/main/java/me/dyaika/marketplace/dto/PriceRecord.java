package me.dyaika.marketplace.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PriceRecord {
    private BigDecimal itemPrice;
    private LocalDateTime priceCheckDate;

    public PriceRecord(BigDecimal itemPrice, LocalDateTime priceCheckDate) {
        this.itemPrice = itemPrice;
        this.priceCheckDate = priceCheckDate;
    }
}
