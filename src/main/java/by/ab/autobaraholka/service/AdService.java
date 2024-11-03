package by.ab.autobaraholka.service;

import by.ab.autobaraholka.model.Ad;
import by.ab.autobaraholka.model.CarFilter;

import java.util.List;


public interface AdService {
     List<Ad> findAllAds();
     List<Ad> findWithFilters(CarFilter filter);
     Ad saveAd(Ad ad, String token);
     Ad findById(Integer id);
     Ad updateAd(Ad ad, String token);
     boolean deleteAd(Integer id, String token) ;

}
