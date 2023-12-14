package me.dyaika.marketplace.dto.responses;

import lombok.Data;
import me.dyaika.marketplace.dto.ActualPriceRecord;

import java.util.List;

@Data
public class GetActualPricesResponse {
    private Long itemId;
    private List<ActualPriceRecord> availability;

    public GetActualPricesResponse(Long itemId, List<ActualPriceRecord> availability) {
        this.itemId = itemId;
        this.availability = availability;
    }
}
