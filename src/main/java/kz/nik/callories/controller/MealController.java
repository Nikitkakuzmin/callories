package kz.nik.callories.controller;

import kz.nik.callories.model.Meal;
import kz.nik.callories.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/meals")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @PostMapping("/{userId}")
    public ResponseEntity<Meal> addMeal(@PathVariable Long userId, @RequestBody Meal meal) {
        return ResponseEntity.ok(mealService.addMeal(userId, meal));
    }

    @GetMapping("/{userId}/report")
    public ResponseEntity<Map<String, Object>> getDailyReport(@PathVariable Long userId, @RequestParam LocalDate date) {
        int calories = mealService.getCaloriesForDay(userId, date);
        boolean withinNorm = mealService.isWithinCalorieNorm(userId, date);

        Map<String, Object> report = new HashMap<>();
        report.put("date", date);
        report.put("consumedCalories", calories);
        report.put("withinNorm", withinNorm);

        return ResponseEntity.ok(report);
    }


    @GetMapping("/{userId}/history")
    public ResponseEntity<List<Map<String, Object>>> getMealHistory(
            @PathVariable Long userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<Map<String, Object>> history = mealService.getMealHistory(userId, startDate, endDate);
        return ResponseEntity.ok(history);
    }
}
