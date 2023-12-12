package me.dyaika.marketplace.repositories;

import me.dyaika.marketplace.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    // Возможно, вы захотите добавить дополнительные методы запросов сюда
}

