package me.dyaika.marketplace.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "item_shop_associations")
@IdClass(ItemShopAssociation.ItemShopAssociationId.class)
public class ItemShopAssociation {
    public ItemShopAssociation() {
    }

    public ItemShopAssociation(Long shopId, Long itemId, String itemUrl) {
        this.shopId = shopId;
        this.itemId = itemId;
        this.itemUrl = itemUrl;
    }

    @Id
    @Column(name = "shop_id")
    private Long shopId;

    @Id
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_url")
    private String itemUrl;

    // Внутренний класс для составного ключа
    @Data
    public static class ItemShopAssociationId implements Serializable {
        private Long shopId;
        private Long itemId;

        public ItemShopAssociationId() {
        }

        public ItemShopAssociationId(Long shopId, Long itemId) {
            this.shopId = shopId;
            this.itemId = itemId;
        }
    }
}

