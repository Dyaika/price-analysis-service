package me.dyaika.marketplace.dto.responses;

import lombok.Data;
import me.dyaika.marketplace.dto.AssociatedItem;
import me.dyaika.marketplace.entities.ItemShopAssociation;

import java.util.List;

@Data
public class GetShopAssociationsResponse {
    private Long shopId;
    private List<AssociatedItem> associatedItems;

    public GetShopAssociationsResponse(Long shopId, List<AssociatedItem> associatedItems) {
        this.shopId = shopId;
        this.associatedItems = associatedItems;
    }
}
