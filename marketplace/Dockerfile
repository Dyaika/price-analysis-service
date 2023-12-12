# Используем официальный образ Gradle для сборки приложения
FROM gradle:8.3-jdk17 AS builder

# Копируем исходный код в контейнер
COPY ./ /app
COPY ./marketplace-properties/marketplace.properties /app/src/main/resources/application.properties
WORKDIR /app

# Очищаем кеш Gradle
RUN gradle clean

# Собираем приложение с помощью Gradle, исключая выполнение тестов
RUN gradle build -x test

RUN gradle bootJar

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/app.jar ./app.jar
CMD ["java", "-jar", "app.jar"]