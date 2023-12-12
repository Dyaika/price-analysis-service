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

    public Item createItem(Item newItem) {
        // Дополнительная логика (если нужна) перед сохранением, например, валидация
        return itemRepository.save(newItem);
    }

    public Item updateItem(Long itemId, Item updatedItem) {
        Optional<Item> optionalExistingItem = itemRepository.findById(itemId);

        if (optionalExistingItem.isPresent()) {
            Item existingItem = optionalExistingItem.get();
            // Обновление полей товара
            existingItem.setItemName(updatedItem.getItemName());
            existingItem.setItemDescription(updatedItem.getItemDescription());
            existingItem.setCategoryId(updatedItem.getCategoryId());

            // Дополнительная логика (если нужна) перед сохранением, например, валидация

            return itemRepository.save(existingItem);
        } else {
            return null; // Товар с указанным ID не найден
        }
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
        // Дополнительная логика после удаления (если нужна)
    }

    // Другие методы, если необходимо
}

