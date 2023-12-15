package me.dyaika.marketplace.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopItem {
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private Integer categoryId;
    private BigDecimal price;

    public ShopItem(Long itemId, String itemName, String itemDescription, Integer categoryId, BigDecimal price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.categoryId = categoryId;
        this.price = price;
    }

    public ShopItem() {
    }

}
