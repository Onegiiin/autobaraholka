package by.ab.autobaraholka.model;

import by.ab.autobaraholka.model.Ad;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "stats")
public class CarStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id")
    private Integer id;

    @Column(name = "stat_year", columnDefinition = "YEAR")
    private int year;

    @Column(name = "stat_mileage")
    private int mileage;

    @Column(name = "stat_engine_type", length = 8)
    private String engineType;

    @Column(name = "stat_power")
    private int power;

    @Column(name = "stat_torque")
    private int torque;

    @Column(name = "stat_engine_displacement")
    private int engineDisplacement;

    @Column(name = "stat_color", length = 50)
    private String color;

    @JsonIgnore
    @OneToOne(mappedBy = "carStats")
    private Ad ad;
}
