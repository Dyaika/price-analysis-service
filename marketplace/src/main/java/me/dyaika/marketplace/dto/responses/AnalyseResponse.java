package me.dyaika.marketplace.dto.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AnalyseResponse {
    private BigDecimal minActualPrice;
    private BigDecimal averageActualPrice;
    private BigDecimal maxActualPrice;
    private BigDecimal minPriceEver;
    private BigDecimal averagePriceEver;
    private BigDecimal maxPriceEver;
    private String recommendation;
    private List<Long> shopsIdWithMinPrice;
}
