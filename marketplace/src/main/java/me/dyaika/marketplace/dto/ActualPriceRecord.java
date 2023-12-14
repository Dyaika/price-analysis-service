package me.dyaika.marketplace.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ActualPriceRecord {
    private Long shopId;
    private String url;
    private BigDecimal price;
    private LocalDateTime checkDate;

    public ActualPriceRecord(Long shopId, String url, BigDecimal price, LocalDateTime checkDate) {
        this.shopId = shopId;
        this.url = url;
        this.price = price;
        this.checkDate = checkDate;
    }
}
