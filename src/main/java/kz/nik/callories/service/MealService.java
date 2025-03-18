package kz.nik.callories.service;

import kz.nik.callories.model.Meal;
import kz.nik.callories.model.User;
import kz.nik.callories.model.Dish;
import kz.nik.callories.repository.DishRepository;
import kz.nik.callories.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final UserService userService;
    private final DishRepository dishRepository;

    public Meal addMeal(Long userId, Meal meal) {
        User user = userService.getUserById(userId);
        meal.setUser(user);
        meal.setDate(LocalDate.now());

        List<Dish> dishes = meal.getDishes().stream()
                .map(dish -> dishRepository.findById(dish.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish not found: " +
                                dish.getId())))
                .collect(Collectors.toList());

        meal.setDishes(dishes);

        return mealRepository.save(meal);
    }

    public List<Meal> getMealsForUser(Long userId, LocalDate date) {
        User user = userService.getUserById(userId);
        return mealRepository.findAllByUserAndDate(user, date);
    }

    public int getCaloriesForDay(Long userId, LocalDate date) {
        return getMealsForUser(userId, date).stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToInt(Dish::getCalories)
                .sum();
    }

    public boolean isWithinCalorieNorm(Long userId, LocalDate date) {
        User user = userService.getUserById(userId);
        int consumedCalories = getCaloriesForDay(userId, date);
        return consumedCalories <= user.getDailyCalorieNorm();
    }
    public List<Map<String, Object>> getMealHistory(Long userId, LocalDate startDate, LocalDate endDate) {
        User user = userService.getUserById(userId);
        List<Meal> meals = mealRepository.findAllByUserAndDateBetween(user, startDate, endDate);

        return meals.stream().collect(Collectors.groupingBy(Meal::getDate))
                .entrySet().stream().map(entry -> {
                    int totalCalories = entry.getValue().stream()
                            .flatMap(meal -> meal.getDishes().stream())
                            .mapToInt(Dish::getCalories).sum();
                    boolean withinNorm = totalCalories <= user.getDailyCalorieNorm();

                    Map<String, Object> report = new HashMap<>();
                    report.put("date", entry.getKey());
                    report.put("consumedCalories", totalCalories);
                    report.put("withinNorm", withinNorm);

                    return report;
                }).collect(Collectors.toList());
    }

}
