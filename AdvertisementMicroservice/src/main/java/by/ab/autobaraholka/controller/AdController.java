package by.ab.autobaraholka.controller;

import by.ab.autobaraholka.model.Ad;
import by.ab.autobaraholka.model.CarFilter;
import by.ab.autobaraholka.service.AdService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/ads")
@AllArgsConstructor
public class AdController {
    private final AdService service;
    @GetMapping
    public List<Ad> findAllAds(){
        return service.findAllAds();
    }

    @GetMapping("/search")
    public List<Ad> findCarByKeyword(@RequestParam String keyword) {
        return service.findCarsByKeyword(keyword);
    }

    @GetMapping("/{adId}/owner_phone")
    public String getOwnerPhoneNumber(@PathVariable Integer adId) {
        return service.getOwnerPhoneNumber(adId);
    }

    @PostMapping("save_ad")
    public String saveAd(@RequestBody Ad ad, @RequestHeader("Authorization") String authorizationHeader) {
        return service.saveAd(ad, getToken(authorizationHeader)) != null ? "Success" : "Failure";
    }
    @GetMapping("/{id}")
    public Ad findById(@PathVariable Integer id){
        return service.findById(id);
    }

    @PutMapping("update_ad")
    public String updateAd(@RequestBody Ad ad, @RequestHeader("Authorization") String authorizationHeader){
        return service.updateAd(ad, getToken(authorizationHeader)) != null ? "Success" : "Failure";
    }

    @DeleteMapping("delete_ad/{id}")
    public String deleteAd(@PathVariable Integer id, @RequestHeader("Authorization") String authorizationHeader){
        return service.deleteAd(id, getToken(authorizationHeader)) ? "Success" : "Failure";
    }

    @GetMapping("filter")
    public List<Ad> findWithFilters(@RequestParam(required = false) String carBrand,
                                      @RequestParam(required = false) String carModel,
                                      @RequestParam(required = false) Integer minPrice,
                                      @RequestParam(required = false) Integer maxPrice,
                                      @RequestParam(required = false) Integer minYear,
                                      @RequestParam(required = false) Integer maxYear,
                                      @RequestParam(required = false) Integer minMileage,
                                      @RequestParam(required = false) Integer maxMileage,
                                      @RequestParam(required = false) String engineType,
                                      @RequestParam(required = false) Integer minPower,
                                      @RequestParam(required = false) Integer maxPower,
                                      @RequestParam(required = false) String[] colors){

        List<String> colorList = colors != null ? List.of(colors) : Collections.emptyList();
        CarFilter filter = new CarFilter(carBrand, carModel, minPrice, maxPrice, minYear, maxYear, minMileage, maxMileage, engineType, minPower, maxPower, colorList);
        return service.findWithFilters(filter);
    }

    private String getToken(String header){
        return header.replace("Bearer ", "").trim();
    }
}
