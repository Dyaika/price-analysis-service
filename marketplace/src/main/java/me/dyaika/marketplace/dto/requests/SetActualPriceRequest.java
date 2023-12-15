package me.dyaika.marketplace.dto.requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SetActualPriceRequest {
    private Long itemId;
    private BigDecimal price;
}
