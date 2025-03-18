package kz.nik.callories.service;

import kz.nik.callories.model.Dish;
import kz.nik.callories.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;

    public Dish createDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }
}
