package me.dyaika.marketplace.dto.responses;

import lombok.Data;

@Data
public class GetItemResponse {

    private Long itemId;
    private String itemName;
    private String itemDescription;
    private Integer categoryId;

    // Конструкторы, геттеры и сеттеры (автоматически добавятся Lombok)

    public GetItemResponse() {
    }

    public GetItemResponse(Long itemId, String itemName, String itemDescription, Integer categoryId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.categoryId = categoryId;
    }
}

