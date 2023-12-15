package me.dyaika.marketplace.dto;

import lombok.Data;

@Data
public class AssociatedItem {
    private Long itemId;
    private String itemUrl;

    public AssociatedItem(Long itemId, String itemUrl) {
        this.itemId = itemId;
        this.itemUrl = itemUrl;
    }
}
