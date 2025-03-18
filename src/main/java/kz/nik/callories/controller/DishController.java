package kz.nik.callories.controller;

import jakarta.validation.Valid;
import kz.nik.callories.model.Dish;
import kz.nik.callories.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @PostMapping
    public ResponseEntity<Dish> createDish(@RequestBody @Valid Dish dish) {
        return ResponseEntity.ok(dishService.createDish(dish));
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getAllDishes() {
        return ResponseEntity.ok(dishService.getAllDishes());
    }
}
