package me.dyaika.marketplace.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemParameterKey implements Serializable {
    private Long itemId;
    private String parameterName;

    // Constructors, equals, and hashCode methods (generated by IDE or manually)
}
