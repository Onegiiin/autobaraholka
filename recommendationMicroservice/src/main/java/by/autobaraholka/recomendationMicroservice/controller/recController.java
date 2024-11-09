package by.autobaraholka.recomendationMicroservice.controller;

import by.autobaraholka.recomendationMicroservice.model.Ad;
import by.autobaraholka.recomendationMicroservice.model.CarFilter;
import by.autobaraholka.recomendationMicroservice.service.RecService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/recommendations")
@AllArgsConstructor
public class recController {
    private final RecService service;

    @PostMapping("save-search/{userId}")
    public ResponseEntity<String> saveSearchRequest(@PathVariable Integer userId, @RequestBody CarFilter filter) {
        return service.saveSearchRequest(filter, userId);
    }

    @GetMapping("get-by-user/{userId}")
    public List<Ad> getRecommendations(@PathVariable Integer userId) {
        return service.getRecommendations(userId);
    }

    @PostMapping("save-opened-ad/{userId}")
    public ResponseEntity<String> saveOpenedAd(@PathVariable Integer userId, @RequestParam Integer adId) {
        return service.saveOpenedAd(userId, adId);
    }

    @GetMapping("get-similar-ads/{adId}")
    public List<Ad> getSimilarAds(@PathVariable Integer adId) {
        return service.getSimilarAds(adId);
    }
}
