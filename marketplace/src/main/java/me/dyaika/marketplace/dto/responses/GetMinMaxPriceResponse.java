package me.dyaika.marketplace.dto.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetMinMaxPriceResponse {
    private Long itemId;
    private BigDecimal price;
    List<ShopRecord> records;

    public GetMinMaxPriceResponse(Long itemId, BigDecimal price, List<ShopRecord> records) {
        this.itemId = itemId;
        this.price = price;
        this.records = records;
    }

    @Data
    public static class ShopRecord{
        public ShopRecord(Long shopId, LocalDateTime priceCheckDate) {
            this.shopId = shopId;
            this.priceCheckDate = priceCheckDate;
        }

        private Long shopId;
        private LocalDateTime priceCheckDate;
    }
}
