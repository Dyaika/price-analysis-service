package me.dyaika.marketplace.responses;

import lombok.Data;

@Data
public class GetNiceItemResponse {

    private Long itemId;
    private String itemName;
    private String itemDescription;
    private String categoryName;

    // Конструкторы, геттеры и сеттеры (автоматически добавятся Lombok)

    public GetNiceItemResponse() {
    }

    public GetNiceItemResponse(Long itemId, String itemName, String itemDescription, String categoryName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.categoryName = categoryName;
    }
}

