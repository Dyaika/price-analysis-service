package me.dyaika.marketplace.dto.responses;

import lombok.Data;
import java.util.HashMap;

@Data
public class GetNiceItemResponse {

    private Long itemId;
    private String itemName;
    private String itemDescription;
    private String categoryName;
    private HashMap<String, String> parameters;

    // Конструкторы, геттеры и сеттеры (автоматически добавятся Lombok)

    public GetNiceItemResponse() {
    }

    public GetNiceItemResponse(Long itemId, String itemName, String itemDescription, String categoryName, HashMap<String, String> parameters) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.categoryName = categoryName;
        this.parameters = parameters;
    }
}

