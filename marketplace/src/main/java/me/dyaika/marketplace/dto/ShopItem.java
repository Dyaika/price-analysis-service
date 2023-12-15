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
    private String url;
    public ShopItem() {
    }

}
