package by.autobaraholka.recomendationMicroservice.repository;

import by.autobaraholka.recomendationMicroservice.model.Ad;
import by.autobaraholka.recomendationMicroservice.model.Recommendation;
import by.autobaraholka.recomendationMicroservice.model.RecommendationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<Recommendation, RecommendationId> {

    @Query("SELECT r.ad FROM Recommendation r WHERE r.user.id = :userId")
    List<Ad> findAdsByUserId(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Recommendation r WHERE r.user.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);

}
