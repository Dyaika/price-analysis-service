package me.dyaika.marketplace.dto.requests;

import lombok.Data;
import me.dyaika.marketplace.dto.ItemParameter;

import java.util.List;

@Data
public class UpdateItemRequest {
    private String itemName;
    private String itemDescription;
    private Integer categoryId;
    private List<ItemParameter> parameters;
}
