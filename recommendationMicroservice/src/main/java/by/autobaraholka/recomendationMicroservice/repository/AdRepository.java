package by.autobaraholka.recomendationMicroservice.repository;

import by.autobaraholka.recomendationMicroservice.model.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Integer> {

}
