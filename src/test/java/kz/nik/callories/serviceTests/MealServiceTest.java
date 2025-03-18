package kz.nik.callories.serviceTests;
import kz.nik.callories.model.Dish;
import kz.nik.callories.model.Meal;
import kz.nik.callories.model.User;
import kz.nik.callories.repository.MealRepository;
import kz.nik.callories.service.MealService;
import kz.nik.callories.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MealServiceTest {
    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private MealService mealService;

    private User testUser;
    private Meal testMeal;
    private Dish testDish;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setDailyCalorieNorm(2000);

        testDish = new Dish();
        testDish.setId(1L);
        testDish.setCalories(500);

        testMeal = new Meal();
        testMeal.setId(1L);
        testMeal.setUser(testUser);
        testMeal.setDate(LocalDate.now());
        testMeal.setDishes(List.of(testDish));
    }

    @Test
    void testAddMeal() {
        when(userService.getUserById(1L)).thenReturn(testUser);
        when(mealRepository.save(any(Meal.class))).thenReturn(testMeal);

        Meal savedMeal = mealService.addMeal(1L, testMeal);

        assertNotNull(savedMeal);
        assertEquals(testUser, savedMeal.getUser());
        assertEquals(LocalDate.now(), savedMeal.getDate());

        verify(mealRepository, times(1)).save(testMeal);
    }

    @Test
    void testGetMealsForUser() {
        when(userService.getUserById(1L)).thenReturn(testUser);
        when(mealRepository.findAllByUserAndDate(testUser, LocalDate.now()))
                .thenReturn(List.of(testMeal));

        List<Meal> meals = mealService.getMealsForUser(1L, LocalDate.now());

        assertNotNull(meals);
        assertEquals(1, meals.size());
        assertEquals(testMeal, meals.get(0));

        verify(mealRepository, times(1)).findAllByUserAndDate(testUser, LocalDate.now());
    }

    @Test
    void testGetCaloriesForDay() {
        when(userService.getUserById(1L)).thenReturn(testUser);
        when(mealRepository.findAllByUserAndDate(testUser, LocalDate.now()))
                .thenReturn(List.of(testMeal));

        int totalCalories = mealService.getCaloriesForDay(1L, LocalDate.now());

        assertEquals(500, totalCalories);

        verify(mealRepository, times(1)).findAllByUserAndDate(testUser, LocalDate.now());
    }

    @Test
    void testIsWithinCalorieNorm() {
        when(userService.getUserById(1L)).thenReturn(testUser);
        when(mealRepository.findAllByUserAndDate(testUser, LocalDate.now()))
                .thenReturn(List.of(testMeal));

        boolean withinNorm = mealService.isWithinCalorieNorm(1L, LocalDate.now());

        assertTrue(withinNorm);

        verify(mealRepository, times(1)).findAllByUserAndDate(testUser, LocalDate.now());
    }

    @Test
    void testIsNotWithinCalorieNorm() {
        testDish.setCalories(2500); // превышаем норму
        when(userService.getUserById(1L)).thenReturn(testUser);
        when(mealRepository.findAllByUserAndDate(testUser, LocalDate.now()))
                .thenReturn(List.of(testMeal));

        boolean withinNorm = mealService.isWithinCalorieNorm(1L, LocalDate.now());

        assertFalse(withinNorm);

        verify(mealRepository, times(1)).findAllByUserAndDate(testUser, LocalDate.now());
    }
}