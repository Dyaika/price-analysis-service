package me.dyaika.marketplace.repositories;

import me.dyaika.marketplace.entities.ItemShopPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemShopPriceRepository extends JpaRepository<ItemShopPrice, Long> {
    List<ItemShopPrice> findByItemIdAndShopIdOrderByPriceCheckDateAsc(Long itemId, Long shopId);
    @Query("SELECT p FROM ItemShopPrice p WHERE p.itemId = :itemId " +
            "AND p.priceCheckDate = (SELECT MAX(p2.priceCheckDate) FROM ItemShopPrice p2 WHERE p2.itemId = :itemId)")
    List<ItemShopPrice> findLatestPricesByItemId(@Param("itemId") Long itemId);
}

