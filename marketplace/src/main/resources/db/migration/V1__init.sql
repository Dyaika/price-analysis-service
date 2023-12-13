CREATE TABLE categories
(
    category_id   INTEGER PRIMARY KEY,
    category_name VARCHAR(50) not null
);

INSERT INTO categories (category_id, category_name)
VALUES (1, 'без категории'),
       (2, 'смартфоны'),
       (3, 'планшеты'),
       (4, 'компьютеры и комплектующие'),
       (5, 'бытовая техника'),
       (6, 'ноутбуки'),
       (7, 'аксессуары'),
       (8, 'кабели');

CREATE TABLE items
(
    item_id          BIGSERIAL PRIMARY KEY,
    item_name        VARCHAR(100) NOT NULL,
    item_description TEXT    DEFAULT NULL,
    category_id      INTEGER DEFAULT 1,
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

INSERT INTO items (item_name, item_description, category_id)
VALUES ('Смартфон Galaxy S21', 'Премиальный смартфон с высокими техническими характеристиками', 2),
       ('Планшет iPad Air', 'Легкий и мощный планшет от Apple', 3),
       ('Ноутбук ThinkPad X1 Carbon', 'Легкий и производительный ноутбук для бизнеса', 6),
       ('Холодильник LG', 'Большой двухдверный холодильник с технологией NoFrost', 5),
       ('Графическая карта NVIDIA RTX 3080', 'Мощная графическая карта для игр и тяжелых задач', 4),
       ('Наушники Sony WH-1000XM4', 'Беспроводные наушники с системой шумоподавления', 7),
       ('HDMI кабель 2.0', 'Высокоскоростной кабель для передачи видео в высоком разрешении', 8),
       ('Беспроводная зарядка Anker', 'Зарядное устройство для смартфонов с поддержкой беспроводной зарядки', 7);

CREATE TABLE item_parameters
(
    item_id         BIGINT,
    parameter_name  VARCHAR(255),
    parameter_value VARCHAR(255),
    PRIMARY KEY (item_id, parameter_name),
    FOREIGN KEY (item_id) REFERENCES items (item_id) ON DELETE CASCADE
);

INSERT INTO item_parameters (item_id, parameter_name, parameter_value)
VALUES (1, 'Цвет', 'Черный'),
       (1, 'Операционная система', 'Android'),
       (1, 'Диагональ экрана', '6.2 дюйма'),
       (1, 'Вес', '163 г'),
       (2, 'Цвет', 'Серый'),
       (2, 'Операционная система', 'iOS'),
       (2, 'Диагональ экрана', '10.9 дюйма'),
       (2, 'Вес', '458 г'),
       (3, 'Цвет', 'Черный'),
       (3, 'Процессор', 'Intel Core i7'),
       (3, 'Оперативная память', '16 ГБ'),
       (3, 'Вес', '1.09 кг'),
       (4, 'Цвет', 'Серебристый'),
       (4, 'Объем', '601 л'),
       (4, 'Тип холодильника', 'Двухдверный'),
       (4, 'Технология', 'NoFrost'),
       (5, 'Тип', 'Графическая карта'),
       (5, 'Чипсет', 'NVIDIA RTX 3080'),
       (5, 'VRAM', '10 ГБ'),
       (6, 'Цвет', 'Черный'),
       (6, 'Тип', 'Беспроводные'),
       (6, 'Активное шумоподавление', 'Да'),
       (6, 'Время работы от батареи', 'До 30 часов'),
       (7, 'Длина', '2 метра'),
       (7, 'Версия', '2.0'),
       (7, 'Поддержка 4K', 'Да'),
       (8, 'Мощность', '10 Вт'),
       (8, 'Поддержка стандартов', 'Qi'),
       (8, 'Цвет', 'Белый');

CREATE TABLE shops
(
    shop_id          BIGSERIAL PRIMARY KEY,
    shop_name        VARCHAR(100) NOT NULL,
    shop_description TEXT DEFAULT NULL,
    shop_url         VARCHAR(255) NOT NULL
);

INSERT INTO shops (shop_name, shop_description, shop_url)
VALUES ('Электроника24', 'Онлайн магазин электроники', 'https://www.electronics24.com'),
       ('ГаджетМаг', 'Широкий ассортимент гаджетов', 'https://www.gadgetmag.com'),
       ('ТехникаМир', 'Специализированный магазин бытовой техники', 'https://www.techworld.com'),
       ('НоутбукШоп', 'Магазин ноутбуков и аксессуаров', 'https://www.laptopshop.com');

CREATE TABLE item_shop_associations
(
    shop_id  BIGINT       NOT NULL,
    item_id  BIGINT       NOT NULL,
    item_url VARCHAR(255) NOT NULL,
    PRIMARY KEY (shop_id, item_id),
    FOREIGN KEY (shop_id) REFERENCES shops (shop_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES items (item_id) ON DELETE CASCADE
);

INSERT INTO item_shop_associations (shop_id, item_id, item_url)
VALUES (1, 1, 'https://www.electronics24.com/smartphone-galaxy-s21'),
       (2, 1, 'https://www.gadgetmag.com/galaxy-s21-special-offer'),
       (3, 1, 'https://www.techworld.com/samsung-galaxy-s21'),
       (2, 2, 'https://www.gadgetmag.com/ipad-air-2023'),
       (3, 2, 'https://www.techworld.com/apple-ipad-air'),
       (1, 3, 'https://www.electronics24.com/thinkpad-x1-carbon-2023'),
       (4, 3, 'https://www.laptopshop.com/thinkpad-business-laptop'),
       (1, 4, 'https://www.electronics24.com/lg-fridge'),
       (2, 4, 'https://www.gadgetmag.com/lg-no-frost-refrigerator'),
       (3, 5, 'https://www.techworld.com/nvidia-rtx-3080-gaming'),
       (2, 6, 'https://www.gadgetmag.com/sony-wh-1000xm4'),
       (3, 6, 'https://www.techworld.com/high-quality-wireless-headphones'),
       (4, 7, 'https://www.laptopshop.com/high-speed-hdmi-cable'),
       (1, 8, 'https://www.electronics24.com/anker-wireless-charger'),
       (2, 8, 'https://www.gadgetmag.com/advanced-wireless-charging-station');

CREATE TABLE item_shop_prices
(
    shop_id          BIGINT         NOT NULL,
    item_id          BIGINT         NOT NULL,
    price_check_date TIMESTAMP DEFAULT NOW(),
    item_price       DECIMAL(22, 2) NOT NULL,
    PRIMARY KEY (shop_id, item_id, price_check_date),
    FOREIGN KEY (shop_id, item_id) REFERENCES item_shop_associations (shop_id, item_id) ON DELETE CASCADE
);

INSERT INTO item_shop_prices (shop_id, item_id, price_check_date, item_price)
VALUES
-- Смартфон Galaxy S21
-- Сентябрь
(1, 1, '2023-09-01', 34999.99),
(2, 1, '2023-09-01', 35900.00),
(3, 1, '2023-09-01', 34980.00),
-- Октябрь
(1, 1, '2023-10-01', 34699.99),
(2, 1, '2023-10-01', 35700.00),
(3, 1, '2023-10-01', 34680.00),
-- Ноябрь
(1, 1, '2023-11-01', 34599.99),
(2, 1, '2023-11-01', 35600.00),
(3, 1, '2023-11-01', 34580.00),
-- Декабрь
(1, 1, '2023-12-01', 34499.99),
(2, 1, '2023-12-01', 35500.00),
(3, 1, '2023-12-01', 34480.00);

-- Планшет iPad Air
INSERT INTO item_shop_prices (shop_id, item_id, price_check_date, item_price)
VALUES
-- Сентябрь
(2, 2, '2023-09-01', 25499.99),
(3, 2, '2023-09-01', 25800.00),
-- Октябрь
(2, 2, '2023-10-01', 24999.99),
(3, 2, '2023-10-01', 25300.00),
-- Ноябрь
(2, 2, '2023-11-01', 24499.99),
(3, 2, '2023-11-01', 24800.00),
-- Декабрь
(2, 2, '2023-12-01', 23999.99),
(3, 2, '2023-12-01', 24300.00);

-- Для товара 3 в разных магазинах
INSERT INTO item_shop_prices (shop_id, item_id, price_check_date, item_price)
VALUES
-- Сентябрь
(1, 3, '2023-09-01', 45999.99),
(4, 3, '2023-09-01', 46000.00),
-- Октябрь
(1, 3, '2023-10-01', 44999.99),
(4, 3, '2023-10-01', 45000.00),
-- Ноябрь
(1, 3, '2023-11-01', 43999.99),
(4, 3, '2023-11-01', 44000.00),
-- Декабрь
(1, 3, '2023-12-01', 42999.99),
(4, 3, '2023-12-01', 43000.00);

-- Для товара 4 в разных магазинах
INSERT INTO item_shop_prices (shop_id, item_id, price_check_date, item_price)
VALUES
-- Сентябрь
(1, 4, '2023-09-01', 25999.99),
(2, 4, '2023-09-01', 26000.00),
-- Октябрь
(1, 4, '2023-10-01', 24999.99),
(2, 4, '2023-10-01', 25000.00),
-- Ноябрь
(1, 4, '2023-11-01', 23999.99),
(2, 4, '2023-11-01', 24000.00),
-- Декабрь
(1, 4, '2023-12-01', 22999.99),
(2, 4, '2023-12-01', 23000.00);


-- Для товара 5 в разных магазинах
INSERT INTO item_shop_prices (shop_id, item_id, price_check_date, item_price)
VALUES
-- Сентябрь
(3, 5, '2023-09-01', 1999.99),
-- Октябрь
(3, 5, '2023-10-01', 1899.99),
-- Ноябрь
(3, 5, '2023-11-01', 1799.99),
-- Декабрь
(3, 5, '2023-12-01', 1699.99);


-- Для товара 6 в разных магазинах
INSERT INTO item_shop_prices (shop_id, item_id, price_check_date, item_price)
VALUES
-- Сентябрь
(2, 6, '2023-09-01', 15000.99),
(3, 6, '2023-09-01', 15050.00),
-- Октябрь
(2, 6, '2023-10-01', 15100.99),
(3, 6, '2023-10-01', 15150.00),
-- Ноябрь
(2, 6, '2023-11-01', 15200.99),
(3, 6, '2023-11-01', 15250.00),
-- Декабрь
(2, 6, '2023-12-01', 15300.99),
(3, 6, '2023-12-01', 15350.00);


-- товар 7
INSERT INTO item_shop_prices (shop_id, item_id, price_check_date, item_price)
VALUES
-- Сентябрь
(4, 7, '2023-09-01', 899.99),
-- Октябрь
(4, 7, '2023-10-01', 879.99),
-- Ноябрь
(4, 7, '2023-11-01', 859.99),
-- Декабрь
(4, 7, '2023-12-01', 839.99);

-- товар 8
INSERT INTO item_shop_prices (shop_id, item_id, price_check_date, item_price)
VALUES
-- Сентябрь
(1, 8, '2023-09-01', 1499.99),
(2, 8, '2023-09-01', 1500.00),
-- Октябрь
(1, 8, '2023-10-01', 1399.99),
(2, 8, '2023-10-01', 1400.00),
-- Ноябрь
(1, 8, '2023-11-01', 1299.99),
(2, 8, '2023-11-01', 1300.00),
-- Декабрь
(1, 8, '2023-12-01', 1199.99),
(2, 8, '2023-12-01', 1200.00);
