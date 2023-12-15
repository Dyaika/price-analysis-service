package me.dyaika.marketplace.services;

import me.dyaika.marketplace.entities.ItemShopAssociation;
import me.dyaika.marketplace.repositories.ItemShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemShopAssociationService {

    private final ItemShopRepository itemShopRepository;

    @Autowired
    public ItemShopAssociationService(ItemShopRepository itemShopRepository) {
        this.itemShopRepository = itemShopRepository;
    }

    @Transactional
    public void addItemToShop(ItemShopAssociation request) {
        itemShopRepository.save(request);
    }

    @Transactional
    public void removeItemFromShop(Long shopId, Long itemId) {
        itemShopRepository.deleteById(new ItemShopAssociation.ItemShopAssociationId(shopId, itemId));
    }

    public ItemShopAssociation updateItemShopAssociation(ItemShopAssociation updatedAssociation) {
        // Попытка найти существующую запись
        Optional<ItemShopAssociation> existingAssociation =
                itemShopRepository.findById(new ItemShopAssociation.ItemShopAssociationId(
                        updatedAssociation.getShopId(),
                        updatedAssociation.getItemId()));

        if (existingAssociation.isPresent()) {
            // Если запись существует, обновляем её URL
            ItemShopAssociation associationToUpdate = existingAssociation.get();
            associationToUpdate.setItemUrl(updatedAssociation.getItemUrl());
            return itemShopRepository.save(associationToUpdate);
        } else {
            // Если запись не существует
            return null;
        }
    }

    public List<ItemShopAssociation> getAssociationsByShopId(Long shopId) {
        return itemShopRepository.findByShopId(shopId);
    }

    public ItemShopAssociation getAssociationById(Long itemId, Long shopId) {
        return itemShopRepository.findById(new ItemShopAssociation.ItemShopAssociationId(shopId, itemId)).orElse(null);
    }
}

