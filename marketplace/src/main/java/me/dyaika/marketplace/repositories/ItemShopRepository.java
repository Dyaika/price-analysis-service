package me.dyaika.marketplace.repositories;

import me.dyaika.marketplace.entities.ItemShopAssociation;
import me.dyaika.marketplace.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemShopRepository extends JpaRepository<ItemShopAssociation, ItemShopAssociation.ItemShopAssociationId> {
    List<ItemShopAssociation> findByShopId(Long shopId);
}

