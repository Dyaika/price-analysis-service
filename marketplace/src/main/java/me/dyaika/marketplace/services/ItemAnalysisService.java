package me.dyaika.marketplace.services;

import me.dyaika.marketplace.dto.ActualPriceRecord;
import me.dyaika.marketplace.dto.responses.AnalyseResponse;
import me.dyaika.marketplace.dto.responses.GetActualPricesResponse;
import me.dyaika.marketplace.dto.responses.GetMinMaxPriceResponse;
import me.dyaika.marketplace.dto.responses.GetPriceHistoryResponse;
import me.dyaika.marketplace.entities.ItemShopPrice;
import me.dyaika.marketplace.repositories.ItemShopPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public BigDecimal getMinPriceAnyShop(Long itemId){
        return itemShopPriceRepository.findMinPriceAnyShop(itemId);
    }

    public BigDecimal getMaxPriceAnyShop(Long itemId){
        return itemShopPriceRepository.findMaxPriceAnyShop(itemId);
    }

    public BigDecimal getAveragePriceAnyShop(Long itemId){
        return itemShopPriceRepository.findAveragePriceAnyShop(itemId).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getMinPriceByItemIdAndShopId(Long itemId, Long shopId) {
        return itemShopPriceRepository.findMinPriceByItemIdAndShopId(itemId, shopId);
    }

    public BigDecimal getMaxPriceByItemIdAndShopId(Long itemId, Long shopId) {
        return itemShopPriceRepository.findMaxPriceByItemIdAndShopId(itemId, shopId);
    }

    public BigDecimal getAveragePriceByItemIdAndShopId(Long itemId, Long shopId) {
        return itemShopPriceRepository.findAveragePriceByItemIdAndShopId(itemId, shopId).setScale(2, RoundingMode.HALF_UP);
    }

    public GetMinMaxPriceResponse getMinActualPrice(Long itemId) {
        BigDecimal price = itemShopPriceRepository.findMinLatestPriceByItemId(itemId);
        List<GetMinMaxPriceResponse.ShopRecord> list = itemShopPriceRepository.findLatestByItemIdAndItemPrice(itemId, price)
                .stream()
                .map(isp -> new GetMinMaxPriceResponse.ShopRecord(isp.getShopId(), isp.getPriceCheckDate()))
                .toList();
        return new GetMinMaxPriceResponse(itemId, price, list);
    }

    public GetMinMaxPriceResponse getMaxActualPrice(Long itemId) {
        BigDecimal price =  itemShopPriceRepository.findMaxLatestPriceByItemId(itemId);
        List<GetMinMaxPriceResponse.ShopRecord> list = itemShopPriceRepository.findLatestByItemIdAndItemPrice(itemId, price)
                .stream()
                .map(isp -> new GetMinMaxPriceResponse.ShopRecord(isp.getShopId(), isp.getPriceCheckDate()))
                .toList();
        return new GetMinMaxPriceResponse(itemId, price, list);
    }

    public BigDecimal getAverageActualPrice(Long itemId) {
        return itemShopPriceRepository.findAvgLatestPriceByItemId(itemId).setScale(2, RoundingMode.HALF_UP);
    }

    public AnalyseResponse analyse(Long itemId){
        AnalyseResponse response = new AnalyseResponse();
        GetMinMaxPriceResponse bestActual = getMinActualPrice(itemId);
        response.setMinActualPrice(bestActual.getPrice());
        response.setAverageActualPrice(getAverageActualPrice(itemId));
        response.setMaxActualPrice(getMaxActualPrice(itemId).getPrice());
        response.setMinPriceEver(getMinPriceAnyShop(itemId));
        response.setAveragePriceEver(getAveragePriceAnyShop(itemId));
        response.setMaxPriceEver(getMaxPriceAnyShop(itemId));
        response.setShopsIdWithMinPrice(bestActual.getRecords().stream().map(GetMinMaxPriceResponse.ShopRecord::getShopId).toList());
        response.setRecommendation(generateRecommendation(response));
        return response;
    }

    private String generateRecommendation(AnalyseResponse response) {
        BigDecimal minActualPrice = response.getMinActualPrice();
        BigDecimal averageActualPrice = response.getAverageActualPrice();
        BigDecimal minPriceEver = response.getMinPriceEver();

        if (minActualPrice.compareTo(minPriceEver) <= 0) {
            // Если текущая цена ниже минимальной цены за все время, рекомендуем покупать сейчас
            return "Рекомендуем покупать сейчас, так как цена минимальная за все время.";
        } else if (minActualPrice.compareTo(averageActualPrice) <= 0) {
            // Если текущая цена ниже средней цены, но выше минимальной за все время, рекомендуем подождать
            return "Покупка допустима, так как цена ниже средней, однако она выше минимальной за все время.";
        } else {
            // В противном случае рекомендуем подождать
            return "Рекомендуем подождать, чтобы получить более выгодную цену.";
        }
    }
}

