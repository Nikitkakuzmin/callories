package kz.nik.callories.repository;

import kz.nik.callories.model.Meal;
import kz.nik.callories.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findAllByUserAndDate(User user, LocalDate date);
    List<Meal> findAllByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
