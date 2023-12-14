package me.dyaika.marketplace.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "item_shop_prices")
@IdClass(ItemShopPrice.ItemShopPriceId.class)
public class ItemShopPrice {

    @Id
    @Column(name = "shop_id")
    private Long shopId;

    @Id
    @Column(name = "item_id")
    private Long itemId;

    @Id
    @Column(name = "price_check_date")
    private LocalDateTime priceCheckDate;

    @Column(name = "item_price")
    private BigDecimal itemPrice;

    // Новое поле для связи с item_shop_associations
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "shop_id", referencedColumnName = "shop_id", insertable = false, updatable = false),
            @JoinColumn(name = "item_id", referencedColumnName = "item_id", insertable = false, updatable = false)
    })
    private ItemShopAssociation itemShopAssociation;

    public ItemShopPrice() {
    }

    public ItemShopPrice(Long shopId, Long itemId, LocalDateTime priceCheckDate, BigDecimal itemPrice) {
        this.shopId = shopId;
        this.itemId = itemId;
        this.priceCheckDate = priceCheckDate;
        this.itemPrice = itemPrice;
    }

    // Внутренний класс для составного ключа
    @Data
    public static class ItemShopPriceId implements Serializable {
        private Long shopId;
        private Long itemId;
        private LocalDateTime priceCheckDate;
    }
}


