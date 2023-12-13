package me.dyaika.marketplace.requests;

import lombok.Data;

import java.util.List;

@Data
public class UpdateItemRequest {
    private String itemName;
    private String itemDescription;
    private Integer categoryId;
    private List<ItemParameterRequest> parameters;
}
