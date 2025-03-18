package kz.nik.callories.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.nik.callories.model.Meal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.ArrayList;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddMeal() throws Exception {
        Meal testMeal = new Meal();
        testMeal.setDate(LocalDate.of(2024, 3, 18));
        testMeal.setDishes(new ArrayList<>());

        mockMvc.perform(post("/meals/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMeal)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDailyReport() throws Exception {
        LocalDate testDate = LocalDate.of(2024, 3, 18);

        mockMvc.perform(get("/meals/1/report")
                        .param("date", testDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2024-03-18"))
                .andExpect(jsonPath("$.consumedCalories").exists())
                .andExpect(jsonPath("$.withinNorm").exists());
    }
}
