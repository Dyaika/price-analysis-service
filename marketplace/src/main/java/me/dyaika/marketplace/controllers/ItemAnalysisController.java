package me.dyaika.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.dyaika.marketplace.dto.responses.AnalyseResponse;
import me.dyaika.marketplace.dto.responses.GetActualPricesResponse;
import me.dyaika.marketplace.dto.responses.GetMinMaxPriceResponse;
import me.dyaika.marketplace.dto.responses.GetPriceHistoryResponse;
import me.dyaika.marketplace.entities.ItemShopPrice;
import me.dyaika.marketplace.services.ItemAnalysisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api(tags = "Анализ цен.", description = "Информация о цене товара в разных магазинах и целесообразности покупки.")
@RestController
@RequestMapping("/analyse")
public class ItemAnalysisController {
    private final ItemAnalysisService analysisService;

    public ItemAnalysisController(ItemAnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @ApiOperation("История цены товара в магазине.")
    @GetMapping("/price-history/{itemId}")
    public ResponseEntity<GetPriceHistoryResponse> getPriceHistory(@PathVariable Long itemId, @RequestParam Long shopId) {
        GetPriceHistoryResponse response = analysisService.getPriceHistory(itemId, shopId);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Просмотр цен на один товар в разных магазинах.")
    @GetMapping("/actual-prices/{itemId}")
    public ResponseEntity<GetActualPricesResponse> getActualPrices(@PathVariable Long itemId){
        GetActualPricesResponse response = new GetActualPricesResponse(
                itemId,
                analysisService.getActualPrices(itemId)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Получить минимальную цену товара за все время.")
    @GetMapping("/min-price/{itemId}")
    public ResponseEntity<BigDecimal> getMinPrice(@PathVariable Long itemId, @RequestParam(required = false) Long shopId) {
        BigDecimal minPrice;
        if (shopId != null) {
            // Если shopId указан, вызываем метод для конкретного магазина
            minPrice = analysisService.getMinPriceByItemIdAndShopId(itemId, shopId);
        } else {
            // Иначе вызываем метод для любого магазина
            minPrice = analysisService.getMinPriceAnyShop(itemId);
        }
        return new ResponseEntity<>(minPrice, HttpStatus.OK);
    }

    @ApiOperation("Получить максимальную цену товара за все время.")
    @GetMapping("/max-price/{itemId}")
    public ResponseEntity<BigDecimal> getMaxPrice(@PathVariable Long itemId, @RequestParam(required = false) Long shopId) {
        BigDecimal maxPrice;
        if (shopId != null) {
            maxPrice = analysisService.getMaxPriceByItemIdAndShopId(itemId, shopId);
        } else {
            maxPrice = analysisService.getMaxPriceAnyShop(itemId);
        }
        return new ResponseEntity<>(maxPrice, HttpStatus.OK);
    }

    @ApiOperation("Получить среднюю цену товара за все время.")
    @GetMapping("/average-price/{itemId}")
    public ResponseEntity<BigDecimal> getAveragePrice(@PathVariable Long itemId, @RequestParam(required = false) Long shopId) {
        BigDecimal averagePrice;
        if (shopId != null) {
            averagePrice = analysisService.getAveragePriceByItemIdAndShopId(itemId, shopId);
        } else {
            averagePrice = analysisService.getAveragePriceAnyShop(itemId);
        }
        return new ResponseEntity<>(averagePrice, HttpStatus.OK);
    }

    @ApiOperation("Получить минимальную актуальную цену товара.")
    @GetMapping("/min-price/{itemId}/actual")
    public ResponseEntity<GetMinMaxPriceResponse> getMinActualPrice(@PathVariable Long itemId) {
        GetMinMaxPriceResponse minActualPriceList = analysisService.getMinActualPrice(itemId);

        if (minActualPriceList != null) {
            return new ResponseEntity<>(minActualPriceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Получить максимальную актуальную цену товара.")
    @GetMapping("/max-price/{itemId}/actual")
    public ResponseEntity<GetMinMaxPriceResponse> getMaxActualPrice(@PathVariable Long itemId) {
        GetMinMaxPriceResponse maxActualPriceList = analysisService.getMaxActualPrice(itemId);

        if (maxActualPriceList != null) {
            return new ResponseEntity<>(maxActualPriceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Получить среднюю актуальную цену товара.")
    @GetMapping("/average-price/{itemId}/actual")
    public ResponseEntity<BigDecimal> getAverageActualPrice(@PathVariable Long itemId) {
        BigDecimal averageActualPrice = analysisService.getAverageActualPrice(itemId);

        if (averageActualPrice != null) {
            return new ResponseEntity<>(averageActualPrice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Анализ товара для покупки.")
    @GetMapping("/{itemId}")
    public ResponseEntity<AnalyseResponse> analyse(@PathVariable Long itemId) {
        AnalyseResponse response = analysisService.analyse(itemId);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
