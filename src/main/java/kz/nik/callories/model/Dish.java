package kz.nik.callories.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "dishes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Min(0)
    private int calories;

    @Min(0)
    private double proteins;

    @Min(0)
    private double fats;

    @Min(0)
    private double carbohydrates;
}
