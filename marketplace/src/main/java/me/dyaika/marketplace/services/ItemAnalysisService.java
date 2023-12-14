package me.dyaika.marketplace.services;

import me.dyaika.marketplace.dto.ActualPriceRecord;
import me.dyaika.marketplace.dto.responses.GetActualPricesResponse;
import me.dyaika.marketplace.dto.responses.GetPriceHistoryResponse;
import me.dyaika.marketplace.entities.ItemShopPrice;
import me.dyaika.marketplace.repositories.ItemShopPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemAnalysisService {

    private final ItemShopPriceRepository itemShopPriceRepository;

    @Autowired
    public ItemAnalysisService(ItemShopPriceRepository itemShopPriceRepository) {
        this.itemShopPriceRepository = itemShopPriceRepository;
    }

    public GetPriceHistoryResponse getPriceHistory(Long itemId, Long shopId) {
        // Используйте ваш репозиторий для получения истории цен
        List<ItemShopPrice> priceHistory = itemShopPriceRepository.findByItemIdAndShopIdOrderByPriceCheckDateAsc(itemId, shopId);

        // Преобразование сущностей в DTO (PriceRecord)
        HashMap<LocalDateTime, BigDecimal> priceMap = priceHistory.stream()
                .collect(Collectors.toMap(
                        ItemShopPrice::getPriceCheckDate,
                        ItemShopPrice::getItemPrice,
                        (price1, price2) -> price2, // В случае дубликатов берем последнюю цену
                        HashMap::new
                ));

        return new GetPriceHistoryResponse(itemId, shopId, priceMap);
    }

    public List<ActualPriceRecord> getActualPrices(Long itemId) {
        List<ItemShopPrice> prices = itemShopPriceRepository.findLatestPricesByItemId(itemId);

        return prices.stream()
                .map(this::mapToActualPriceRecord)
                .collect(Collectors.toList());
    }

    private ActualPriceRecord mapToActualPriceRecord(ItemShopPrice price) {
        return new ActualPriceRecord(
                price.getShopId(),
                price.getItemShopAssociation().getItemUrl(),
                price.getItemPrice(),
                price.getPriceCheckDate()
        );
    }
}

