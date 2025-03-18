package kz.nik.callories.serviceTests;

import kz.nik.callories.model.Dish;
import kz.nik.callories.repository.DishRepository;
import kz.nik.callories.service.DishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DishServiceTest {
    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;

    private Dish testDish;

    @BeforeEach
    void setUp() {
        testDish = new Dish(1L, "Куриная грудка", 165, 31, 3.6, 0);
    }

    @Test
    void testCreateDish() {
        when(dishRepository.save(any(Dish.class))).thenReturn(testDish);

        Dish createdDish = dishService.createDish(testDish);

        assertNotNull(createdDish);
        assertEquals("Куриная грудка", createdDish.getName());
        assertEquals(165, createdDish.getCalories());

        verify(dishRepository, times(1)).save(testDish);
    }

    @Test
    void testGetAllDishes() {
        List<Dish> dishes = Arrays.asList(
                new Dish(1L, "Куриная грудка", 165, 31, 3.6, 0),
                new Dish(2L, "Овсянка", 150, 5, 3, 27)
        );

        when(dishRepository.findAll()).thenReturn(dishes);

        List<Dish> result = dishService.getAllDishes();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Овсянка", result.get(1).getName());

        verify(dishRepository, times(1)).findAll();
    }
}