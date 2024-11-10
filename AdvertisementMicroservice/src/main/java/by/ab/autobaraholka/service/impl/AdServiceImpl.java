package by.ab.autobaraholka.service.impl;

import by.ab.autobaraholka.model.*;
import by.ab.autobaraholka.repository.AdRepository;
import by.ab.autobaraholka.service.AdService;
import by.ab.autobaraholka.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdServiceImpl implements AdService {
     private final AdRepository repository;
     private final UserService userService;
     @Override
     public List<Ad> findAllAds() {
          return repository.findAll();
     }

     @Override
     public List<Ad> findCarsByKeyword(String keyword) {
          String[] keywords = keyword.split("\\s+");

          return repository.findAll().stream()
                  .filter(ad -> Arrays.stream(keywords)
                          .anyMatch(word -> ad.getCarBrand().toLowerCase().contains(word.toLowerCase()) ||
                                  ad.getCarModel().toLowerCase().contains(word.toLowerCase())))
                  .collect(Collectors.toList());
     }
     @Override
     public String getOwnerPhoneNumber(Integer adId) {
          return repository.findById(adId)
                  .map(ad -> ad.getAuthor().getPhone())
                  .orElse("Owner not found");
     }

     @Override
     public List<Ad> findWithFilters(CarFilter filter) {
          return repository.findAll().stream()
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
                  .filter(ad -> filter.getColors().isEmpty() || filter.getColors().stream().anyMatch(s -> s.equalsIgnoreCase(ad.getCarStats().getColor())))
                  .collect(Collectors.toList());
     }

     @Override
     public Ad saveAd(Ad ad, String token) {
          User author = userService.findByToken(token);
          if (author == null){
               return null;
          }
          ad.setAuthor(author);
          return repository.save(ad);
     }

     @Override
     public Ad findById(Integer id) {
          return repository.findById(id).orElse(null);
     }

     @Override
     public Ad updateAd(Ad ad, String token){
          Ad existing = repository.findById(ad.getId()).orElse(null);
          if (existing != null && existing.getAuthor().getId().equals(userService.findByToken(token).getId())){
               return repository.save(ad);
          }
          return null;
     }

     @Override
     public boolean deleteAd(Integer id, String token) {
          Ad existing = repository.findById(id).orElse(null);
          if (existing != null && existing.getAuthor().getId().equals(userService.findByToken(token).getId())){
               repository.delete(existing);
               return true;
          }
          return false;
     }
}
