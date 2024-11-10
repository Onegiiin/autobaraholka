package by.autobaraholka.recomendationMicroservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "recommendation")
public class Recommendation {
    @EmbeddedId
    private RecommendationId id;

    @ManyToOne
    @MapsId("adId")
    @JoinColumn(name = "r_ad_id")
    private Ad ad;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "r_user_id")
    private User user;
}
