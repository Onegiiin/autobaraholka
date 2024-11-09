package by.autobaraholka.recomendationMicroservice.service;


import by.autobaraholka.recomendationMicroservice.model.Ad;
import by.autobaraholka.recomendationMicroservice.model.CarFilter;
import by.autobaraholka.recomendationMicroservice.repository.AdRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecService  {
     private final AdRepository repository;

     public ResponseEntity<String> saveSearchRequest(CarFilter filter, Integer userId) {

     }

     public List<Ad> getRecommendations(Integer userId) {

     }

     public ResponseEntity<String> saveOpenedAd(Integer userId, Integer adId) {

     }

     public List<Ad> getSimilarAds(Integer adId) {

     }
}
