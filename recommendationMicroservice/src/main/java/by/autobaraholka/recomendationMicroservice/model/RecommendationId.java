package by.autobaraholka.recomendationMicroservice.model;


import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class RecommendationId implements Serializable {
    private Integer adId;
    private Integer userId;
}