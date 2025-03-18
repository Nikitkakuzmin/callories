package kz.nik.callories.controllerTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.nik.callories.model.Dish;
import kz.nik.callories.service.DishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class DishControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DishService dishService;

    @InjectMocks
    private kz.nik.callories.controller.DishController dishController;

    private Dish testDish;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dishController).build();

        testDish = new Dish();
        testDish.setId(1L);
        testDish.setName("Омлет");
        testDish.setCalories(250);
    }

    @Test
    void testCreateDish() throws Exception {
        when(dishService.createDish(any(Dish.class))).thenReturn(testDish);

        mockMvc.perform(post("/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testDish)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Омлет"))
                .andExpect(jsonPath("$.calories").value(250));

        verify(dishService, times(1)).createDish(any(Dish.class));
    }

    @Test
    void testGetAllDishes() throws Exception {
        List<Dish> dishes = Arrays.asList(testDish);
        when(dishService.getAllDishes()).thenReturn(dishes);

        mockMvc.perform(get("/dishes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Омлет"));

        verify(dishService, times(1)).getAllDishes();
    }
}