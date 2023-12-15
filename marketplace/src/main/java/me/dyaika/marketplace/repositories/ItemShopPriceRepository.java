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

    @Query("SELECT MIN(p.itemPrice) FROM ItemShopPrice p WHERE p.itemId = :itemId")
    BigDecimal findMinPriceAnyShop(@Param("itemId") Long itemId);

    @Query("SELECT MAX(p.itemPrice) FROM ItemShopPrice p WHERE p.itemId = :itemId")
    BigDecimal findMaxPriceAnyShop(@Param("itemId") Long itemId);

    @Query("SELECT AVG(p.itemPrice) FROM ItemShopPrice p WHERE p.itemId = :itemId")
    BigDecimal findAveragePriceAnyShop(@Param("itemId") Long itemId);

    @Query("SELECT MIN(p.itemPrice) FROM ItemShopPrice p WHERE p.itemId = :itemId AND p.shopId = :shopId")
    BigDecimal findMinPriceByItemIdAndShopId(@Param("itemId") Long itemId, @Param("shopId") Long shopId);

    @Query("SELECT MAX(p.itemPrice) FROM ItemShopPrice p WHERE p.itemId = :itemId AND p.shopId = :shopId")
    BigDecimal findMaxPriceByItemIdAndShopId(@Param("itemId") Long itemId, @Param("shopId") Long shopId);

    @Query("SELECT AVG(p.itemPrice) FROM ItemShopPrice p WHERE p.itemId = :itemId AND p.shopId = :shopId")
    BigDecimal findAveragePriceByItemIdAndShopId(@Param("itemId") Long itemId, @Param("shopId") Long shopId);

    @Query("SELECT MIN(p.itemPrice) FROM ItemShopPrice p WHERE p.itemId = :itemId AND (p.shopId, p.itemId, p.priceCheckDate) IN " +
            "(SELECT ps.shopId, ps.itemId, MAX(ps.priceCheckDate) FROM ItemShopPrice ps WHERE ps.itemId = :itemId GROUP BY ps.shopId, ps.itemId)")
    BigDecimal findMinLatestPriceByItemId(@Param("itemId") Long itemId);

    @Query("SELECT MAX(p.itemPrice) FROM ItemShopPrice p WHERE p.itemId = :itemId AND (p.shopId, p.itemId, p.priceCheckDate) IN " +
            "(SELECT ps.shopId, ps.itemId, MAX(ps.priceCheckDate) FROM ItemShopPrice ps WHERE ps.itemId = :itemId GROUP BY ps.shopId, ps.itemId)")
    BigDecimal findMaxLatestPriceByItemId(@Param("itemId") Long itemId);

    @Query("SELECT AVG(p.itemPrice) FROM ItemShopPrice p WHERE p.itemId = :itemId AND (p.shopId, p.itemId, p.priceCheckDate) IN " +
            "(SELECT ps.shopId, ps.itemId, MAX(ps.priceCheckDate) FROM ItemShopPrice ps WHERE ps.itemId = :itemId GROUP BY ps.shopId, ps.itemId)")
    BigDecimal findAvgLatestPriceByItemId(@Param("itemId") Long itemId);

    List<ItemShopPrice> findByItemIdAndItemPrice(Long itemId, BigDecimal itemPrice);
    List<ItemShopPrice> findByItemIdAndShopIdAndItemPrice(Long itemId, Long shopId, BigDecimal itemPrice);

    @Query("SELECT p FROM ItemShopPrice p WHERE p.itemId = :itemId AND p.itemPrice = :itemPrice AND (p.shopId, p.itemId, p.priceCheckDate) IN " +
            "(SELECT ps.shopId, ps.itemId, MAX(ps.priceCheckDate) FROM ItemShopPrice ps WHERE ps.itemId = :itemId GROUP BY ps.shopId, ps.itemId)")
    List<ItemShopPrice> findLatestByItemIdAndItemPrice(@Param("itemId") Long itemId, @Param("itemPrice") BigDecimal itemPrice);
}

