package me.dyaika.marketplace.dto.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

@Data
public class GetPriceHistoryResponse {
    private Long itemId;
    private Long shopId;
    private HashMap<LocalDateTime, BigDecimal> prices;

    public GetPriceHistoryResponse(Long itemId, Long shopId, HashMap<LocalDateTime, BigDecimal> prices) {
        this.itemId = itemId;
        this.shopId = shopId;
        this.prices = prices;
    }
}
