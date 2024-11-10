package by.autobaraholka.recomendationMicroservice.service;


import by.autobaraholka.recomendationMicroservice.model.*;
import by.autobaraholka.recomendationMicroservice.repository.AdRepository;
import by.autobaraholka.recomendationMicroservice.repository.RecommendationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecService {
    private final AdRepository adRepository;
    private final RecommendationRepository recRepository;

    public ResponseEntity<String> saveSearchRequest(CarFilter filter, Integer userId) {

        int priceMargin = 10;
        int powerMargin = 10;

        if (filter.getMinPrice() != null) {
            int minPrice = filter.getMinPrice() - (filter.getMinPrice() * priceMargin / 100);
            filter.setMinPrice(minPrice);
        }

        if (filter.getMaxPrice() != null) {
            int maxPrice = filter.getMaxPrice() + (filter.getMaxPrice() * priceMargin / 100);
            filter.setMaxPrice(maxPrice);
        }

        if (filter.getMinPower() != null) {
            int minPower = filter.getMinPower() - (filter.getMinPower() * powerMargin / 100);
            filter.setMinPower(minPower);
        }

        if (filter.getMaxPower() != null) {
            int maxPower = filter.getMaxPower() + (filter.getMaxPower() * powerMargin / 100);
            filter.setMaxPower(maxPower);
        }

        List<Ad> similarAds = findWithFilters(filter);

        if (similarAds.size() > 20) {
            similarAds = similarAds.subList(0, 20);
        }


        recRepository.deleteByUserId(userId);
        User user = new User(userId);
        for (Ad ad : similarAds) {
            Recommendation recommendation = new Recommendation();

            RecommendationId recommendationId = new RecommendationId();
            recommendationId.setAdId(ad.getId());
            recommendationId.setUserId(userId);

            recommendation.setId(recommendationId);
            recommendation.setAd(ad);
            recommendation.setUser(user);

            recRepository.save(recommendation);
        }


        return ResponseEntity.ok("Recommendations saved successfully.");
    }

    public List<Ad> findWithFilters(CarFilter filter) {
        List<Ad> similarAds = new ArrayList<>();
        try {
            similarAds = adRepository.findAll().stream()
                    .filter(ad -> filter.getCarBrand() == null || ad.getCarBrand().equalsIgnoreCase(filter.getCarBrand()))
                    .filter(ad -> filter.getCarModel() == null || ad.getCarModel().equalsIgnoreCase(filter.getCarModel()))
                    .filter(ad -> filter.getMinPrice() == null || ad.getPrice() >= filter.getMinPrice())
                    .filter(ad -> filter.getMaxPrice() == null || ad.getPrice() <= filter.getMaxPrice())
                    .filter(ad -> filter.getMinYear() == null || ad.getCarStats().getYear() >= filter.getMinYear()) // Предполагается, что у Ad есть метод getStats()
                    .filter(ad -> filter.getMaxYear() == null || ad.getCarStats().getYear() <= filter.getMaxYear()) // Предполагается, что у Ad есть метод getStats()
                    .filter(ad -> filter.getMinMileage() == null || ad.getCarStats().getMileage() >= filter.getMinMileage())
                    .filter(ad -> filter.getMaxMileage() == null || ad.getCarStats().getMileage() <= filter.getMaxMileage())
                    .filter(ad -> filter.getEngineType() == null || ad.getCarStats().getEngineType().equalsIgnoreCase(filter.getEngineType()))
                    .filter(ad -> filter.getMinPower() == null || ad.getCarStats().getPower() >= filter.getMinPower())
                    .filter(ad -> filter.getMaxPower() == null || ad.getCarStats().getPower() <= filter.getMaxPower())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return similarAds;
    }

    public List<Ad> findByModel(Integer adId, boolean findOpened) {
        Ad ad = adRepository.findById(adId).orElse(null);
        if (ad == null)
            return Collections.emptyList();

        return adRepository.findAll().stream()
                .filter(a -> a.getCarBrand().equalsIgnoreCase(ad.getCarBrand()))
                .filter(a -> a.getCarModel().equalsIgnoreCase(ad.getCarModel()))
                .filter(a -> findOpened || !a.getId().equals(ad.getId()))
                .limit(10)
                .collect(Collectors.toList());
    }


    public List<Ad> getRecommendations(Integer userId) {
        return recRepository.findAdsByUserId(userId);
    }

    public ResponseEntity<String> saveOpenedAd(Integer userId, Integer adId) {
        User user = new User(userId);
        for (Ad ad : findByModel(adId, true)) {
            Recommendation recommendation = new Recommendation();
            RecommendationId recommendationId = new RecommendationId();
            recommendationId.setAdId(ad.getId());
            recommendationId.setUserId(userId);
            if (!recRepository.existsById(recommendationId)) {
                recommendation.setId(recommendationId);
                recommendation.setAd(ad);
                recommendation.setUser(user);
                recRepository.save(recommendation);
            }
        }
        return null;
    }

    public List<Ad> getSimilarAds(Integer adId) {
        return findByModel(adId, false);
    }
}
