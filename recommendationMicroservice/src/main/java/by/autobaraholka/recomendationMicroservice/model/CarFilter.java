package by.autobaraholka.recomendationMicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CarFilter {
    private String carBrand;      // Фильтр по марке автомобиля
    private String carModel;      // Фильтр по модели автомобиля
    private Integer minPrice;     // Минимальная цена
    private Integer maxPrice;     // Максимальная цена
    private Integer minYear;         // Год выпуска
    private Integer maxYear;         // Год выпуска
    private Integer minMileage;    // Минимальный пробег
    private Integer maxMileage;    // Максимальный пробег
    private String engineType;     // Тип двигателя
    private Integer minPower;      // Минимальная мощность
    private Integer maxPower;      // Максимальная мощность
    private List<String> colors;          // Цвет автомобиля
}