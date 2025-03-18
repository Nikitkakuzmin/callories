package kz.nik.callories.serviceTests;
import kz.nik.callories.model.Goal;
import kz.nik.callories.model.User;
import kz.nik.callories.repository.UserRepository;
import kz.nik.callories.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Иван");
        testUser.setEmail("ivan@example.com");
        testUser.setAge(30);
        testUser.setWeight(80);
        testUser.setHeight(175);
        testUser.setGoal(Goal.LOSS);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User createdUser = userService.createUser(testUser);

        assertNotNull(createdUser);
        assertEquals("Иван", createdUser.getName());
        assertEquals("ivan@example.com", createdUser.getEmail());

        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals("Иван", foundUser.getName());
        assertEquals(30, foundUser.getAge());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.getUserById(2L);
        });

        assertEquals("404 NOT_FOUND \"User not found\"", exception.getMessage());

        verify(userRepository, times(1)).findById(2L);
    }
}