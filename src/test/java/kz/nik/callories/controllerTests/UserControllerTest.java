package kz.nik.callories.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.nik.callories.model.Goal;
import kz.nik.callories.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateUser() throws Exception {
        User testUser = new User();
        testUser.setName("Иван");
        testUser.setEmail("ivan@example.com");
        testUser.setAge(30);
        testUser.setWeight(80);
        testUser.setHeight(180);
        testUser.setGoal(Goal.LOSS);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Иван"))
                .andExpect(jsonPath("$.email").value("ivan@example.com"));
    }

    @Test
    void testGetUser() throws Exception {
        long testUserId = 1L;

        mockMvc.perform(get("/users/" + testUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUserId));
    }
}
