package me.dyaika.marketplace.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "item_shop_associations")
@IdClass(ItemShopAssociation.ItemShopAssociationId.class)
public class ItemShopAssociation {

    @Id
    @Column(name = "shop_id")
    private Long shopId;

    @Id
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_url")
    private String itemUrl;

    // Другие поля и методы, если необходимо

    // Внутренний класс для составного ключа
    @Data
    public static class ItemShopAssociationId implements Serializable {
        private Long shopId;
        private Long itemId;
    }
}

