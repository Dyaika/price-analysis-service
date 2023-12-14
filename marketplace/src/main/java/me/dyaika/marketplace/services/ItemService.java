package me.dyaika.marketplace.services;

import me.dyaika.marketplace.entities.Item;
import me.dyaika.marketplace.repositories.ItemParameterRepository;
import me.dyaika.marketplace.repositories.ItemRepository;
import me.dyaika.marketplace.dto.requests.CreateItemRequest;
import me.dyaika.marketplace.dto.ItemParameter;
import me.dyaika.marketplace.dto.requests.UpdateItemRequest;
import me.dyaika.marketplace.dto.responses.GetNiceItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemParameterRepository parameterRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, ItemParameterRepository parameterRepository) {
        this.itemRepository = itemRepository;
        this.parameterRepository = parameterRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        return optionalItem.orElse(null);
    }

    public Long createItem(CreateItemRequest request) {
        Item newItem = new Item();
        newItem.setItemName(request.getItemName());
        newItem.setItemDescription(request.getItemDescription());
        newItem.setCategoryId(request.getCategoryId());

        // Сохранение товара в репозитории
        newItem = itemRepository.save(newItem);

        // Сохранение параметров товара
        if (request.getParameters() != null) {
            for (ItemParameter parameterRequest : request.getParameters()) {
                me.dyaika.marketplace.entities.ItemParameter parameter = new me.dyaika.marketplace.entities.ItemParameter();
                parameter.setItemId(newItem.getItemId());
                parameter.setParameterName(parameterRequest.getParameterName());
                parameter.setParameterValue(parameterRequest.getParameterValue());
                parameterRepository.save(parameter);
            }
        }

        return newItem.getItemId();
    }

    public Long updateItem(Long itemId, UpdateItemRequest updateRequest) {
        Optional<Item> optionalExistingItem = itemRepository.findById(itemId);

        if (optionalExistingItem.isPresent()) {
            Item existingItem = optionalExistingItem.get();

            // Обновление полей товара
            existingItem.setItemName(updateRequest.getItemName());
            existingItem.setItemDescription(updateRequest.getItemDescription());
            existingItem.setCategoryId(updateRequest.getCategoryId());

            // Удаление старых параметров
            parameterRepository.deleteByItemId(itemId);

            // Добавление новых параметров
            List<ItemParameter> newParameters = updateRequest.getParameters();
            if (newParameters != null) {
                for (ItemParameter parameterRequest : newParameters) {
                    me.dyaika.marketplace.entities.ItemParameter parameter = new me.dyaika.marketplace.entities.ItemParameter();
                    parameter.setItemId(itemId);
                    parameter.setParameterName(parameterRequest.getParameterName());
                    parameter.setParameterValue(parameterRequest.getParameterValue());
                    parameterRepository.save(parameter);
                }
            }

            // Дополнительная логика (если нужна) перед сохранением, например, валидация

            return itemRepository.save(existingItem).getItemId();
        } else {
            return null; // Товар с указанным ID не найден
        }
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
        // Дополнительная логика после удаления (если нужна)
    }

    public List<me.dyaika.marketplace.entities.ItemParameter> findParameters(Long itemId){
        return parameterRepository.findByItemId(itemId);
    }

    public GetNiceItemResponse getNiceItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);

        if (item != null) {
            List<me.dyaika.marketplace.entities.ItemParameter> parameters = findParameters(itemId);
            HashMap<String, String> params = convertParametersToMap(parameters);

            GetNiceItemResponse response = new GetNiceItemResponse(
                    item.getItemId(),
                    item.getItemName(),
                    item.getItemDescription(),
                    item.getCategory().getCategoryName(),
                    params
            );
            return response;
        }

        return null;
    }

    private HashMap<String, String> convertParametersToMap(List<me.dyaika.marketplace.entities.ItemParameter> parameters) {
        HashMap<String, String> paramsMap = new HashMap<>();

        for (me.dyaika.marketplace.entities.ItemParameter parameter : parameters) {
            paramsMap.put(parameter.getParameterName(), parameter.getParameterValue());
        }

        return paramsMap;
    }
}

