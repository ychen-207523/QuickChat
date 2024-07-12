package ychen.quickchat.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ychen.quickchat.model.User;
import ychen.quickchat.repository.UserRepository;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByUsername() {
        // Arrange
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);

        // Act
        User foundUser = userService.findByUsername(username);

        // Assert
        assertEquals(username, foundUser.getUsername());
    }

    @Test
    public void testFindByUsername_UserNotFound() {
        // Arrange
        String username = "nonexistent";
        when(userRepository.findByUsername(username)).thenReturn(null);

        // Act
        User foundUser = userService.findByUsername(username);

        // Assert
        assertNull(foundUser);
    }

    @Test
    public void testFindByUsername_Exception() {
        // Arrange
        String username = "testuser";
        when(userRepository.findByUsername(username)).thenThrow(new RuntimeException("Database error"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            userService.findByUsername(username);
        });
    }

}
