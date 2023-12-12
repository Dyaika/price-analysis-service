package me.dyaika.marketplace.services;

import me.dyaika.marketplace.entities.Item;
import me.dyaika.marketplace.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        return optionalItem.orElse(null);
    }

    // Другие методы, если необходимо, например, для создания, обновления, удаления товара
}

