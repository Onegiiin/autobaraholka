package by.autobaraholka.recomendationMicroservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ad")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id")
    private Integer id;

    @Column(name = "ad_car_brand")
    private String carBrand;

    @Column(name = "ad_car_model")
    private String carModel;

    @Column(name = "ad_price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "ad_author_id")
    private User author;

    @OneToOne
    @JoinColumn(name = "ad_car_stats")
    private CarStats carStats;
}
