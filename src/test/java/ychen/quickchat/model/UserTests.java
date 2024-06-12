package ychen.quickchat.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTests {

    private User user;

    @BeforeEach
    public void setup() {
        user = new User("johndoe", "john.doe@example.com", "Test@123");
    }

    @Test
    public void testGetAndSetId() {
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    public void testGetAndSetUsername() {
        user.setUsername("janedoe");
        assertEquals("janedoe", user.getUsername());
    }

    @Test
    public void testGetAndSetEmail() {
        user.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", user.getEmail());
    }

    @Test
    public void testGetAndSetPassword() {
        user.setPassword("Test@12345");
        assertEquals("Test@12345", user.getPassword());
    }

    @Test
    public void testToString() {
        String expected = "User{id=1, username='johndoe', email='john.doe@example.com', password='Test@123'}";
        user.setId(1L);
        assertEquals(expected, user.toString());
    }
}
