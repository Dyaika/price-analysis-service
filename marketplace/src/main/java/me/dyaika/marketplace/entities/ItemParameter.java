package me.dyaika.marketplace.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "item_parameters")
@IdClass(ItemParameterKey.class) // Указываем класс ключа
public class ItemParameter implements Serializable {

    @Id
    @Column(name = "item_id")
    private Long itemId;

    @Id
    @Column(name = "parameter_name")
    private String parameterName;

    @Column(name = "parameter_value")
    private String parameterValue;

    public ItemParameter(Long itemId, String parameterName, String parameterValue) {
        this.itemId = itemId;
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public ItemParameter() {
    }
}
