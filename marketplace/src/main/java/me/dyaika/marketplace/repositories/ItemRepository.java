package me.dyaika.marketplace.repositories;
import me.dyaika.marketplace.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // Дополнительные методы запросов, если необходимо
}

