package kz.nik.callories.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @Min(10)
    private int age;

    @Min(30)
    @Max(300)
    private double weight;

    @Min(100)
    @Max(250)
    private double height;

    @Enumerated(EnumType.STRING)
    private Goal goal;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private double dailyCalorieNorm;

    public void calculateDailyCalories() {
        if (weight > 0 && height > 0 && age > 0) {
            double bmr;
            if (gender == Gender.FEMALE) {
                bmr = 10 * weight + 6.25 * height - 5 * age - 161;
            } else {
                bmr = 10 * weight + 6.25 * height - 5 * age + 5;
            }

            switch (goal) {
                case LOSS:
                    this.dailyCalorieNorm = bmr * 0.8;
                    break;
                case MAINTENANCE:
                    this.dailyCalorieNorm = bmr;
                    break;
                case GAIN:
                    this.dailyCalorieNorm = bmr * 1.2;
                    break;
            }
        }
    }
}
