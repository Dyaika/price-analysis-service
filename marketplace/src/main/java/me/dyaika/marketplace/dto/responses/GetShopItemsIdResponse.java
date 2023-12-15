package me.dyaika.marketplace.dto.responses;

import lombok.Data;

import java.util.List;

@Data
public class GetShopItemsIdResponse {
    private Long shopId;
    private List<Long> itemsId;

    public GetShopItemsIdResponse(Long shopId, List<Long> itemsId) {
        this.shopId = shopId;
        this.itemsId = itemsId;
    }
}
