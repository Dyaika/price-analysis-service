package me.dyaika.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.dyaika.marketplace.dto.responses.GetActualPricesResponse;
import me.dyaika.marketplace.dto.responses.GetPriceHistoryResponse;
import me.dyaika.marketplace.services.ItemAnalysisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
