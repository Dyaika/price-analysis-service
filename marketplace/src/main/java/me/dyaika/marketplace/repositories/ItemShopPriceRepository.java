package me.dyaika.marketplace.repositories;

import me.dyaika.marketplace.entities.ItemShopPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ItemShopPriceRepository extends JpaRepository<ItemShopPrice, Long> {
    List<ItemShopPrice> findByItemIdAndShopIdOrderByPriceCheckDateAsc(Long itemId, Long shopId);
    @Query("SELECT p FROM ItemShopPrice p WHERE p.itemId = :itemId AND (p.shopId, p.itemId, p.priceCheckDate) IN " +
            "(SELECT ps.shopId, ps.itemId, MAX(ps.priceCheckDate) FROM ItemShopPrice ps WHERE ps.itemId = :itemId GROUP BY ps.shopId, ps.itemId)")
    List<ItemShopPrice> findLatestPricesByItemId(@Param("itemId") Long itemId);


}

