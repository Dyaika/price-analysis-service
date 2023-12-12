package me.dyaika.marketplace.entities;

import javax.persistence.*;

@Entity
@Table(name = "shops") // Указываем имя таблицы в базе данных
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "shop_name", nullable = false, length = 100) // Указываем имя колонки, настройки и ограничения
    private String shopName;

    @Column(name = "shop_description", columnDefinition = "TEXT DEFAULT NULL") // Указываем имя колонки и значение по умолчанию
    private String shopDescription;

    @Column(name = "shop_url", nullable = false, length = 255) // Указываем имя колонки, настройки и ограничения
    private String shopUrl;

    // Конструкторы, геттеры, сеттеры и другие методы

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }
}
