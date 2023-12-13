package me.dyaika.marketplace.repositories;

import me.dyaika.marketplace.entities.ItemParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemParameterRepository extends JpaRepository<ItemParameter, Long> {

    List<ItemParameter> findByItemId(Long itemId);
    @Transactional
    void deleteByItemId(Long itemId);
}
